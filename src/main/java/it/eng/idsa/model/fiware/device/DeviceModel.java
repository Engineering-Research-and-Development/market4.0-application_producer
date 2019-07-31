package it.eng.idsa.model.fiware.device;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DeviceModel {
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
	private String color;
	private String image;
	private List<String> annotations;
	@NotNull(message = "{device.null.category}")
	private String category;
	private String supportedProtocol;
	@Pattern(message = "{device.wrong.type}", regexp = "DeviceModel", flags=Pattern.Flag.CASE_INSENSITIVE)
	private String type;
	private String deviceClass;
	@NotNull(message = "{device.null.controlledProperty}")
	private List<String> controlledProperty;
	private List<String> function ;
	private List<String> supportedUnits;
	private String energyLimitationClass;
	private String documentation;
	@NotNull(message = "{device.null.brandName}")
	private String brandName;
	@NotNull(message = "{device.null.modelName}")
	private String modelName;
	@NotNull(message = "{device.null.manufacturerName}")
	private String manufacturerName;
	
	
	public DeviceModel() {}


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


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public List<String> getAnnotations() {
		return annotations;
	}


	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
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


	public String getDeviceClass() {
		return deviceClass;
	}


	public void setDeviceClass(String deviceClass) {
		this.deviceClass = deviceClass;
	}


	public List<String> getControlledProperty() {
		return controlledProperty;
	}


	public void setControlledProperty(List<String> controlledProperty) {
		this.controlledProperty = controlledProperty;
	}


	public List<String> getFunction() {
		return function;
	}


	public void setFunction(List<String> function) {
		this.function = function;
	}


	public List<String> getSupportedUnits() {
		return supportedUnits;
	}


	public void setSupportedUnits(List<String> supportedUnits) {
		this.supportedUnits = supportedUnits;
	}


	public String getEnergyLimitationClass() {
		return energyLimitationClass;
	}


	public void setEnergyLimitationClass(String energyLimitationClass) {
		this.energyLimitationClass = energyLimitationClass;
	}


	public String getDocumentation() {
		return documentation;
	}


	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}


	public String getBrandName() {
		return brandName;
	}


	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public String getModelName() {
		return modelName;
	}


	public void setModelName(String modelName) {
		this.modelName = modelName;
	}


	public String getManufacturerName() {
		return manufacturerName;
	}


	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	
	
	
}
