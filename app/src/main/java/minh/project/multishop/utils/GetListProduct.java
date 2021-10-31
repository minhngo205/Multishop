package minh.project.multishop.utils;

import android.os.Handler;

import java.util.List;

import minh.project.multishop.models.Product;
import minh.project.multishop.network.IAppAPI;

public class GetListProduct extends Thread{
    List<Product> productList;
    IAppAPI api;
    Handler mHandler;
}
