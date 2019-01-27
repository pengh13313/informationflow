package com.example.a11708.informationflow.Model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Beans {

    public String reason;
    public ResultBean result;
    public int error_code;

    public static List<Beans> arrayBeansFromData(String str) {

        Type listType = new TypeToken<ArrayList<Beans>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }


    public static class ResultBean {
        public String stat;
        public List<DataBean> data;

        public static List<ResultBean> arrayResultBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static class DataBean implements MultiItemEntity {
            public String uniquekey;
            public String title;
            public String date;
            public String category;
            public String author_name;
            public String url;
            public String thumbnail_pic_s;
            public String thumbnail_pic_s02;
            public String thumbnail_pic_s03;

            public static List<DataBean> arrayDataBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<DataBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            @Override
            public int getItemType() {
                if (thumbnail_pic_s03 == null && title != null) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
}
