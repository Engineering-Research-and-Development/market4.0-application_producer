package it.eng.idsa.model.fiware.device;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import it.eng.idsa.model.fiware.common.Address;
import it.eng.idsa.model.fiware.common.Location;

/**
 * The Device class is a Java implementation of the Device FIWARE Data Model
 * 
 * 
 * @author  Gabriele De Luca, Milan Karajovic
 */
public class Device {
	@NotNull(message = "{device.null.id}")
	private String id;
	private String dateCreated;
	private String dateModified;
	private String source;
	private String name;
	private String alternateName;
	private String description;
	private String dataProvider;
	private List<String> owner;
	private Location location;
	private Address address;
	@NotNull(message = "{device.null.category}")
	private String category;
	@NotNull(message = "{device.null.controlledProperty}")
	private List<String> controlledProperty;
	private String supportedProtocol;
	@NotNull(message = "{device.null.type}")
	@Pattern(message = "{device.wrong.type}", regexp = "Device", flags=Pattern.Flag.CASE_INSENSITIVE)
	private String type;
	private List<String> controlledAsset;
	private String mnc;
	private List<String> macAddress;
	private List<String> ipAddress;
	private String configuration;
	private String dateInstalled;
	private String dateFirstUsed;
	private String dateManufactured;
	private String hardwareVersion;
	private String softwareVersion;
	private String firmwareVersion;
	private String osVersion;
	private String dateLastCalibration;
	private String serialNumber;
	private String provider;
	private String refDeviceModel;
	private Float batteryLevel;
	private String deviceState;
	private String dateLastValueReported;
	private String value;

	
	public Device () {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlternateName() {
		return alternateName;
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(String dataProvider) {
		this.dataProvider = dataProvider;
	}

	public List<String> getOwner() {
		return owner;
	}

	public void setOwner(List<String> owner) {
		this.owner = owner;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getControlledProperty() {
		return controlledProperty;
	}

	public void setControlledProperty(List<String> controlledProperty) {
		this.controlledProperty = controlledProperty;
	}

	public String getSupportedProtocol() {
		return supportedProtocol;
	}

	public void setSupportedProtocol(String supportedProtocol) {
		this.supportedProtocol = supportedProtocol;
	}

	public String getType() {
		return type;
	}

	public List<String> getControlledAsset() {
		return controlledAsset;
	}

	public void setControlledAsset(List<String> controlledAsset) {
		this.controlledAsset = controlledAsset;
	}

	public String getMnc() {
		return mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public List<String> getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(List<String> macAddress) {
		this.macAddress = macAddress;
	}

	public List<String> getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(List<String> ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getDateInstalled() {
		return dateInstalled;
	}

	public void setDateInstalled(String dateInstalled) {
		this.dateInstalled = dateInstalled;
	}

	public String getDateFirstUsed() {
		return dateFirstUsed;
	}

	public void setDateFirstUsed(String dateFirstUsed) {
		this.dateFirstUsed = dateFirstUsed;
	}

	public String getDateManufactured() {
		return dateManufactured;
	}

	public void setDateManufactured(String dateManufactured) {
		this.dateManufactured = dateManufactured;
	}

	public String getHardwareVersion() {
		return hardwareVersion;
	}

	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getDateLastCalibration() {
		return dateLastCalibration;
	}

	public void setDateLastCalibration(String dateLastCalibration) {
		this.dateLastCalibration = dateLastCalibration;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getRefDeviceModel() {
		return refDeviceModel;
	}

	public void setRefDeviceModel(String refDeviceModel) {
		this.refDeviceModel = refDeviceModel;
	}

	public Float getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(Float batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public String getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}

	public String getDateLastValueReported() {
		return dateLastValueReported;
	}

	public void setDateLastValueReported(String dateLastValueReported) {
		this.dateLastValueReported = dateLastValueReported;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
