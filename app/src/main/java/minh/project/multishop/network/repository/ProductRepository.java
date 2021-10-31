package minh.project.multishop.network.repository;

import java.util.ArrayList;
import java.util.List;

import minh.project.multishop.models.Product;
import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.RetroInstance;

public class ProductRepository {
    private final IAppAPI api;
    private static final String TAG = "ProductRepository";
    private List<Product> result;

    public ProductRepository() {
        api = RetroInstance.getRetroInstance().create(IAppAPI.class);
        result = new ArrayList<>();
    }
}