package it.eng.idsa;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
* The DAPSInteraction class is responsible for token acquisition and validation
* fully compatible with Fraunhofer AISEC DAPS Server.
*
* @author  Gabriele De Luca, Milan Karajovic
*/
public class DAPSInteraction {
	private static final Logger LOG = Logger.getLogger(DAPSInteraction.class);
	private static DAPSInteraction instance;
	private Key privKey;
	private Certificate cert;
	private PublicKey publicKey;


	public DAPSInteraction() {}
	public static DAPSInteraction getInstance() {
		if (instance == null) {
			instance = new DAPSInteraction();
		}
		return instance;
	}

	/**
	 * Method to aquire a Dynamic Attribute Token (DAT) from a Dynamic Attribute Provisioning Service (DAPS)
	 *
	 * @param targetDirectory - The directory the keystore resides in
	 * @param dapsUrl - the token acquisition URL (e.g., http://daps.aisec.fraunhofer.de:8080, the suffix (/token.php) is added automatically
	 * @param keyStoreName - name of the keystore file (e.g., server-keystore.jks)
	 * @param keyStorePassword - password of keystore
	 * @param keystoreAliasName - alias of the connector's key entry. For default keystores with only one entry, this is '1'
	 * @param connectorUUID - The UUID used to register the connector at the DAPS. Should be replaced by working code that does this automatically
	 */
	public String acquireToken(Path targetDirectory, String dapsUrl, String keyStoreName, String keyStorePassword, String keystoreAliasName, String connectorUUID) {
		LOG.debug("Resolving path for keystore: " + keyStoreName);
		LOG.debug("Path to resolve: " + targetDirectory);
		String token="";
		try (InputStream jksInputStream = Files.newInputStream(targetDirectory.resolve(keyStoreName))) {
			KeyStore store = KeyStore.getInstance("JKS");
			store.load(jksInputStream, keyStorePassword.toCharArray());
			// get private key
			privKey = (PrivateKey) store.getKey(keystoreAliasName, keyStorePassword.toCharArray());
			// Get certificate of public key
			//X509Certificate cert = (X509Certificate)store.getCertificate(keystoreAliasName);
			//connecturUUID = TODO: Get X509 principal, get SubjectDN, parse Common Name
			cert = store.getCertificate(keystoreAliasName);
			// Get public key
			publicKey = cert.getPublicKey();
			LOG.debug("p_key1="+publicKey);

			byte[] encodedPublicKey = publicKey.getEncoded();
			String b64PublicKey = Base64.getEncoder().encodeToString(encodedPublicKey);

			LOG.debug("*************Public Key*****************");
			LOG.debug(b64PublicKey);

			LOG.debug("Private key loaded. Retrieving Dynamic Attribute Token...");

			//create signed JWT (JWS)
			//Create expiry date one day (86400 seconds) from now
			Date expiryDate = Date.from(Instant.now().plusSeconds(86400));
			JwtBuilder jwtb = Jwts.builder()
					.setIssuer(connectorUUID)
					.setSubject(connectorUUID)
					.setExpiration(expiryDate)
					.setIssuedAt(Date.from(Instant.now()))
					.setAudience("https://api.localhost")
					.setNotBefore(Date.from(Instant.now()));

			String jws = jwtb.signWith( SignatureAlgorithm.RS256, privKey).compact();
			/** This is for the old DAPS
            LOG.info("Constructed JWS: " + jws);
            String json =
            "{\"grant_type\": \"urn:ietf:params:oauth:grant-type:jwt-bearer\","
              + "\"assertion\":\""
              + jws
              + "\"}";
			 */
			String json =
					"{\"\": \"\","
							+ "\"\":\"urn:ietf:params:oauth:client-assertion-type:jwt-bearer\","
							+ "\"\":\""
							+ jws + "\","
							+ "\"\":\"\","
							+ "}";



			LOG.debug("POSTing with JSON header " + json);
			OkHttpClient client = new OkHttpClient();
			RequestBody formBody = new FormBody.Builder()
					.add("grant_type", "client_credentials")
					.add("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
					.add("client_assertion", jws)
					//.add("scope", "ids_connector security_level")
					.build();
			Request request = new Request.Builder().url(dapsUrl).post(formBody).build();
			Response response = client.newCall(request).execute();

			String body=response.body().string();
			ObjectNode node = new ObjectMapper().readValue(body, ObjectNode.class);
			//          
			//actually, try and *reuse* a single instance of ObjectMapper

			if (node.has("access_token")) {
				token=node.get("access_token").asText();
				LOG.debug("access_token: " + token);
			}    
			LOG.debug("Response: " + response.toString());
			LOG.debug("Body: " + body);
			LOG.debug("Message: " + response.message());

			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);


			return token;

		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | IOException e) {
			LOG.debug("Cannot acquire token:"+e);
			e.printStackTrace();
		}
		//TODO: Get Token, return it
		return token;
	}

	// Build the public key from modulus and exponent
	public static PublicKey getPublicKey (String modulusB64u, String exponentB64u) throws NoSuchAlgorithmException, InvalidKeySpecException{
		//conversion to BigInteger. I have transformed to Hex because new BigDecimal(byte) does not work for me
		byte exponentB[] = Base64.getUrlDecoder().decode(exponentB64u);
		byte modulusB[] = Base64.getUrlDecoder().decode(modulusB64u);
		BigInteger exponent = new BigInteger(toHexFromBytes(exponentB), 16);
		BigInteger modulus = new BigInteger(toHexFromBytes(modulusB), 16);

		//Build the public key
		RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey pub = factory.generatePublic(spec);

		return pub;
	}


	private static final String[] HEX_TABLE = new String[]{
			"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0a", "0b", "0c", "0d", "0e", "0f",
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1a", "1b", "1c", "1d", "1e", "1f",
			"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2a", "2b", "2c", "2d", "2e", "2f",
			"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3a", "3b", "3c", "3d", "3e", "3f",
			"40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4a", "4b", "4c", "4d", "4e", "4f",
			"50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5a", "5b", "5c", "5d", "5e", "5f",
			"60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6a", "6b", "6c", "6d", "6e", "6f",
			"70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7a", "7b", "7c", "7d", "7e", "7f",
			"80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8a", "8b", "8c", "8d", "8e", "8f",
			"90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9a", "9b", "9c", "9d", "9e", "9f",
			"a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "aa", "ab", "ac", "ad", "ae", "af",
			"b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "ba", "bb", "bc", "bd", "be", "bf",
			"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "ca", "cb", "cc", "cd", "ce", "cf",
			"d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "da", "db", "dc", "dd", "de", "df",
			"e0", "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "ea", "eb", "ec", "ed", "ee", "ef",
			"f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "fa", "fb", "fc", "fd", "fe", "ff",
	};

	public static String toHexFromBytes(byte[] bytes) {
		StringBuffer rc = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			rc.append(HEX_TABLE[0xFF & bytes[i]]);
		}
		return rc.toString();
	}

	
	/**
	 * Method to validate a Dynamic Attribute Token (DAT) from a Dynamic Attribute Provisioning Service (DAPS)
	 *
	 * @param tokenValue - The directory the keystore resides in
	 * @param dapsJWKSUrl - the token acquisition URL (e.g., http://daps.aisec.fraunhofer.de:8080, the suffix (/token.php) is added automatically
	*/
	public boolean validateAuthorization(String tokenValue, String dapsJWKSUrl) {
		try {
			// Set up a JWT processor to parse the tokens and then check their signature
			// and validity time window (bounded by the "iat", "nbf" and "exp" claims)
			ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<SecurityContext>();

			// The public RSA keys to validate the signatures will be sourced from the
			// OAuth 2.0 server's JWK set, published at a well-known URL. The RemoteJWKSet
			// object caches the retrieved keys to speed up subsequent look-ups and can
			// also gracefully handle key-rollover
			JWKSource<SecurityContext> keySource = new RemoteJWKSet<SecurityContext>(new URL("https://daps.aisec.fraunhofer.de/.well-known/jwks.json"));
		
			
			// Load JWK set from URL
			JWKSet publicKeys = JWKSet.load(new URL(dapsJWKSUrl));
			RSAKey key = (RSAKey) publicKeys.getKeyByKeyId("default");
	
			// The expected JWS algorithm of the access tokens (agreed out-of-band)
			JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;

			// Configure the JWT processor with a key selector to feed matching public
			// RSA keys sourced from the JWK set URL
			JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<SecurityContext>(expectedJWSAlg, keySource);
			jwtProcessor.setJWSKeySelector(keySelector);


			/*
			jwtProcessor.setJWTClaimsVerifier(new DefaultJWTClaimsVerifier() {      
				@Override
				public void verify(JWTClaimsSet claimsSet) 
						throws BadJWTException {

					super.verify(claimsSet);

					// If present the actual expiration timestamp is checked by the 
					// overridden method
					if (claimsSet.getExpirationTime() == null) {
						throw new BadJWTException("Missing token expiration claim");
					}

					if (! "https://demo.c2id.com/c2id".equals(claimsSet.getIssuer())) {
						throw new BadJWTException("Token issuer not accepted");
					}
				}
			});
			 */

			//Validate signature
			String exponentB64u = key.getPublicExponent().toString();
			String modulusB64u = key.getModulus().toString();

			//Build the public key from modulus and exponent
			PublicKey publicKey = getPublicKey (modulusB64u,exponentB64u);

			//print key as PEM (base64 and headers)
			String publicKeyPEM = 
					"-----BEGIN PUBLIC KEY-----\n" 
							+ Base64.getEncoder().encodeToString(publicKey.getEncoded()) +"\n"
							+ "-----END PUBLIC KEY-----";
			LOG.debug(  publicKeyPEM);

			//get signed data and signature from JWT
			String signedData = tokenValue.substring(0, tokenValue.lastIndexOf("."));
			String signatureB64u = tokenValue.substring(tokenValue.lastIndexOf(".")+1,tokenValue.length());
			byte signature[] = Base64.getUrlDecoder().decode(signatureB64u);

			//verify Signature
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initVerify(publicKey);
			sig.update(signedData.getBytes());
			boolean v = sig.verify(signature);
			LOG.debug("result_validation_signature="+v);

			if (v==false)
				return v;

			// Process the token
			SecurityContext ctx = null; // optional context parameter, not required here
			JWTClaimsSet claimsSet = jwtProcessor.process(tokenValue, ctx);

			// Print out the token claims set
			LOG.debug("claimsSet="+claimsSet.toJSONObject());

			return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			return false;
		}
	}
	

}
