package com.example.mymap;

import android.content.Context;

import com.example.mymap.database.MyLocation;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class DataLocations {
    public static ArrayList<MyLocation> mData;
    private static boolean mExit  = false;

    private DataLocations() {
    }

    public static void GenerateData(Context context)
    {
        if (!mExit)
        {
            ArrayList<Integer> listImages1 = new ArrayList<>();
            ArrayList<Integer> listImages2 = new ArrayList<>();
            ArrayList<Integer> listImages3 = new ArrayList<>();
            ArrayList<Integer> listImages4 = new ArrayList<>();
            ArrayList<Integer> listImages5 = new ArrayList<>();
            ArrayList<Integer> listImages6 = new ArrayList<>();
            ArrayList<Integer> listImages7 = new ArrayList<>();

            listImages1.add(R.drawable.nha_tho_duc_ba_1);
            listImages1.add(R.drawable.nha_tho_duc_ba_2);
            listImages1.add(R.drawable.nha_tho_duc_ba_3);

            listImages2.add(R.drawable.sg_central_post_1);
            listImages2.add(R.drawable.sg_central_post_2);
            listImages2.add(R.drawable.sg_central_post_3);

            listImages3.add(R.drawable.dinh_doc_lap_1);
            listImages3.add(R.drawable.dinh_doc_lap_2);
            listImages3.add(R.drawable.dinh_doc_lap_3);

            listImages4.add(R.drawable.duong_sach_1);
            listImages4.add(R.drawable.duong_sach_2);
            listImages4.add(R.drawable.duong_sach_3);

            listImages5.add(R.drawable.ben_thanh_1);
            listImages5.add(R.drawable.ben_thanh_2);
            listImages5.add(R.drawable.ben_thanh_3);

            listImages6.add(R.drawable.nguyen_hue_1);
            listImages6.add(R.drawable.nguyen_hue_2);
            listImages6.add(R.drawable.nguyen_hue_3);

            listImages7.add(R.drawable.thao_cam_vien_1);
            listImages7.add(R.drawable.thao_cam_vien_2);
            listImages7.add(R.drawable.thao_cam_vien_3);

            mData = new ArrayList<>();
            mData.add(new MyLocation(R.mipmap.ducba2, "Nhà thờ Đức Bà", listImages1,
                    context.getString(R.string.nhathoducba_info), new LatLng(10.781165,106.6953696)));
            mData.add(new MyLocation(R.mipmap.buudien3, "Bưu điện trung tâm", listImages2,
                    context.getString(R.string.buudienthanhpho_info), new LatLng(10.7798632,106.6977188)));
            mData.add(new MyLocation(R.mipmap.ddl2, "Dinh Độc Lập", listImages3,
                    context.getString(R.string.dinhdoclap_info), new LatLng(10.7771107,106.6954445)));
            mData.add(new MyLocation(R.mipmap.duong_sach, "Đường Sách", listImages4,
                    context.getString(R.string.duongsach_info), new LatLng(10.7801218,106.6970439)));
            mData.add(new MyLocation(R.mipmap.chobenthanh2, "Chợ Bến Thành", listImages5,
                    context.getString(R.string.chobenthanh_info), new LatLng(10.7721148,106.6960897)));
            mData.add(new MyLocation(R.mipmap.phodibo, "Phố đi bộ Nguyễn Huệ", listImages6,
                    context.getString(R.string.phodibonguyenhue_info), new LatLng(10.7741144,106.7014351)));
            mData.add(new MyLocation(R.mipmap.thaocamvien, "Thảo Cầm Viên", listImages7,
                    context.getString(R.string.thaocamvien_info), new LatLng(10.7879492,106.7028015)));

            mExit = true;
        }
    }


}
