package it.eng.idsa.model.fiware.device;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import it.eng.idsa.model.fiware.common.Attribute;
import it.eng.idsa.model.fiware.common.MultiAttribute;


/**
 * The DeviceModelNormalized class is a Java implementation of the DeviceModel Normalized FIWARE Data Model
 * 
 * 
 * @author  Gabriele De Luca, Milan Karajovic
 */
public class DeviceModelNormalized {

	@NotNull(message = "{device.null.id}")
	private String id;
	private Attribute dateCreated;
	private Attribute dateModified;
	private Attribute source;
	private Attribute name;
	private Attribute alternateName;
	private Attribute description;
	private Attribute dataProvider;
	private MultiAttribute owner;
	private Attribute color;
	private Attribute image;
	private MultiAttribute annotations;
	@NotNull(message = "{device.null.category}")
	private MultiAttribute category;
	private Attribute supportedProtocol;
	@Pattern(message = "{device.wrong.type}", regexp = "DeviceModel", flags=Pattern.Flag.CASE_INSENSITIVE)
	private Attribute type;
	private Attribute deviceClass;
	@NotNull(message = "{device.null.controlledProperty}")
	private MultiAttribute controlledProperty;
	private MultiAttribute function ;
	private MultiAttribute supportedUnits;
	private Attribute energyLimitationClass;
	private Attribute documentation;
	@NotNull(message = "{device.null.brandName}")
	private Attribute brandName;
	@NotNull(message = "{device.null.modelName}")
	private Attribute modelName;
	@NotNull(message = "{device.null.manufacturerName}")
	private Attribute manufacturerName;
	
	
	public DeviceModelNormalized() {
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Attribute getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(Attribute dateCreated) {
		this.dateCreated = dateCreated;
	}


	public Attribute getDateModified() {
		return dateModified;
	}


	public void setDateModified(Attribute dateModified) {
		this.dateModified = dateModified;
	}


	public Attribute getSource() {
		return source;
	}


	public void setSource(Attribute source) {
		this.source = source;
	}


	public Attribute getName() {
		return name;
	}


	public void setName(Attribute name) {
		this.name = name;
	}


	public Attribute getAlternateName() {
		return alternateName;
	}


	public void setAlternateName(Attribute alternateName) {
		this.alternateName = alternateName;
	}


	public Attribute getDescription() {
		return description;
	}


	public void setDescription(Attribute description) {
		this.description = description;
	}


	public Attribute getDataProvider() {
		return dataProvider;
	}


	public void setDataProvider(Attribute dataProvider) {
		this.dataProvider = dataProvider;
	}


	public MultiAttribute getOwner() {
		return owner;
	}


	public void setOwner(MultiAttribute owner) {
		this.owner = owner;
	}


	public Attribute getColor() {
		return color;
	}


	public void setColor(Attribute color) {
		this.color = color;
	}


	public Attribute getImage() {
		return image;
	}


	public void setImage(Attribute image) {
		this.image = image;
	}


	public MultiAttribute getAnnotations() {
		return annotations;
	}


	public void setAnnotations(MultiAttribute annotations) {
		this.annotations = annotations;
	}


	public MultiAttribute getCategory() {
		return category;
	}


	public void setCategory(MultiAttribute category) {
		this.category = category;
	}


	public Attribute getSupportedProtocol() {
		return supportedProtocol;
	}


	public void setSupportedProtocol(Attribute supportedProtocol) {
		this.supportedProtocol = supportedProtocol;
	}


	public Attribute getType() {
		return type;
	}


	public void setType(Attribute type) {
		this.type = type;
	}


	public Attribute getDeviceClass() {
		return deviceClass;
	}


	public void setDeviceClass(Attribute deviceClass) {
		this.deviceClass = deviceClass;
	}


	public MultiAttribute getControlledProperty() {
		return controlledProperty;
	}


	public void setControlledProperty(MultiAttribute controlledProperty) {
		this.controlledProperty = controlledProperty;
	}


	public MultiAttribute getFunction() {
		return function;
	}


	public void setFunction(MultiAttribute function) {
		this.function = function;
	}


	public MultiAttribute getSupportedUnits() {
		return supportedUnits;
	}


	public void setSupportedUnits(MultiAttribute supportedUnits) {
		this.supportedUnits = supportedUnits;
	}


	public Attribute getEnergyLimitationClass() {
		return energyLimitationClass;
	}


	public void setEnergyLimitationClass(Attribute energyLimitationClass) {
		this.energyLimitationClass = energyLimitationClass;
	}


	public Attribute getDocumentation() {
		return documentation;
	}


	public void setDocumentation(Attribute documentation) {
		this.documentation = documentation;
	}


	public Attribute getBrandName() {
		return brandName;
	}


	public void setBrandName(Attribute brandName) {
		this.brandName = brandName;
	}


	public Attribute getModelName() {
		return modelName;
	}


	public void setModelName(Attribute modelName) {
		this.modelName = modelName;
	}


	public Attribute getManufacturerName() {
		return manufacturerName;
	}


	public void setManufacturerName(Attribute manufacturerName) {
		this.manufacturerName = manufacturerName;
	}


	
	


}
