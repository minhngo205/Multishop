package minh.project.multishop.models;

import java.util.List;

import minh.project.multishop.network.dtos.DTOmodels.DTOBrand;

public class Category {
    private int ID;
    private String name;
    private List<DTOBrand> brandList;

    public Category(int ID, String name, List<DTOBrand> brandList) {
        this.ID = ID;
        this.name = name;
        this.brandList = brandList;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DTOBrand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<DTOBrand> brandList) {
        this.brandList = brandList;
    }
}