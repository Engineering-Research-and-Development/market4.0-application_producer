package it.eng.idsa.model.fiware.building;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import it.eng.idsa.model.fiware.common.Address;
import it.eng.idsa.model.fiware.common.Location;
import it.eng.idsa.model.fiware.common.OpeningHours;

public class Building {
	@NotNull(message = "{building.null.id}")
	private String id;
	@Pattern(message = "{building.wrong.type}", regexp = "Building", flags=Pattern.Flag.CASE_INSENSITIVE)
	private String type;
	private String source;
	private String dataProvider;
	private String dateCreated;
	private String dateModified;
	private List<String> owner;
	private Location location;
	private Address address;
	@NotNull(message = "{building.null.category}")
	private String category;
	private Location containedInPlace;
	private String description;
	private List<String> occupier ;
	private Integer  floorsAboveGround;
	private Integer floorsBelowGround ;
	private String mapUrl;
	private List<OpeningHours> openingHours;

	public Building() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(String dataProvider) {
		this.dataProvider = dataProvider;
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

	public List<String> getOwner() {
		return owner;
	}

	public void setOwner(List<String> owner) {
		this.owner = owner;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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

	public Location getContainedInPlace() {
		return containedInPlace;
	}

	public void setContainedInPlace(Location containedInPlace) {
		this.containedInPlace = containedInPlace;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getOccupier() {
		return occupier;
	}

	public void setOccupier(List<String> occupier) {
		this.occupier = occupier;
	}

	public Integer getFloorsAboveGround() {
		return floorsAboveGround;
	}

	public void setFloorsAboveGround(Integer floorsAboveGround) {
		this.floorsAboveGround = floorsAboveGround;
	}

	public Integer getFloorsBelowGround() {
		return floorsBelowGround;
	}

	public void setFloorsBelowGround(Integer floorsBelowGround) {
		this.floorsBelowGround = floorsBelowGround;
	}

	public String getMapUrl() {
		return mapUrl;
	}

	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	public List<OpeningHours> getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(List<OpeningHours> openingHours) {
		this.openingHours = openingHours;
	}
	


}
