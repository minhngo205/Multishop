package minh.project.multishop.models;

import java.util.List;

public class Product {
    private int ID;
    private String productName;
    private String imageThumbnail;
    private Brand brand;
    private Category category;
    private List<ProductSpecs> specs;
    private String shortDes;
    private String description;
    private int productPrice;
    private int salePrice;
    private int isDiscount;
    private double avgRate;
    private List<Rating> ratingList;
    private String[] imageList;

    public Product(int ID, String productName, String imageThumbnail, Brand brand, Category category, List<ProductSpecs> specs, String shortDes, String description, int productPrice, int salePrice, int isDiscount, double avgRate, List<Rating> ratingList, String[] imageList) {
        this.ID = ID;
        this.productName = productName;
        this.imageThumbnail = imageThumbnail;
        this.brand = brand;
        this.category = category;
        this.specs = specs;
        this.shortDes = shortDes;
        this.description = description;
        this.productPrice = productPrice;
        this.salePrice = salePrice;
        this.isDiscount = isDiscount;
        this.avgRate = avgRate;
        this.ratingList = ratingList;
        this.imageList = imageList;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<ProductSpecs> getSpecs() {
        return specs;
    }

    public void setSpecs(List<ProductSpecs> specs) {
        this.specs = specs;
    }

    public String getShortDes() {
        return shortDes;
    }

    public void setShortDes(String shortDes) {
        this.shortDes = shortDes;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(int isDiscount) {
        this.isDiscount = isDiscount;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public String[] getImageList() {
        return imageList;
    }

    public void setImageList(String[] imageList) {
        this.imageList = imageList;
    }
}
