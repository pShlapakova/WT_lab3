package by.bsuir.wt_lab.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class AmusementPark
        implements Serializable {
    private ArrayList<Attraction> attractions;
    private ArrayList<ServiceBuilding> serviceBuildings;
    private ArrayList<Territory> territories;

    public AmusementPark() {
        attractions = new ArrayList<Attraction>();
        serviceBuildings = new ArrayList<ServiceBuilding>();
        territories = new ArrayList<Territory>();
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(ArrayList<Territory> territories) {
        this.territories = territories;
    }

    public int getNewTerritoryID(){
        if (territories.isEmpty()){
            return 1;
        } else {
            Collections.sort(territories);
            return territories.get(territories.size()-1).getId() + 1;
        }
    }

    public ArrayList<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
    }

    public int getNewAttractionID(){
        if (attractions.isEmpty()){
            return 1;
        }else{
            Collections.sort(attractions);
            return attractions.get(attractions.size()-1).getId() + 1;
        }
    }

    public ArrayList<ServiceBuilding> getServiceBuildings() {
        return serviceBuildings;
    }

    public void setServiceBuildings(ArrayList<ServiceBuilding> serviceBuildings) {
        this.serviceBuildings = serviceBuildings;
    }

    public int getNewServiceBuildingID(){
        if (serviceBuildings.isEmpty()){
            return 1;
        }else{
            Collections.sort(serviceBuildings);
            return serviceBuildings.get(serviceBuildings.size()-1).getId() + 1;
        }
    }
}
