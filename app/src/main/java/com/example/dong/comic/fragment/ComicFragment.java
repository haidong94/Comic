package com.example.dong.comic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dong.comic.R;

/**
 * Created by DONG on 10-Apr-17.
 */

public class ComicFragment extends Fragment {
    TextView txtContent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_comic,container,false);
        txtContent= (TextView) view.findViewById(R.id.txtContent);
        txtContent.setText("Ô long viện Linh vật sống là bộ truyện dài kì tác giả Au, Yao-hsing dày công xây dựng. Lấy nguồn gốc từ truyền thuyết Tần Thuỷ Hoàng tìm thuốc trường sinh bất tử, bộ truyện này xoay quanh củ nhân sâm ngàn năm Linh vật sống hiện hình và đi tìm lại những phần thân thể của mình lưu lạc bốn phương. Bốn thầy trò Ô long viện bị lôi vào vòng xoáy tìm linh vật, phải chiến đấu với những kẻ ác dữ dội như Lâm công công, Hồ lô bà... Đan xen giữa truyền thống ngàn xưa với những bi hài của cuộc sống hiện đại, bộ truyện cuốn hút từng tập một, từ đầu đến cuối. Tác giả vẫn phát huy thế mạnh là xây dựng từng nhân vật mang cá tính rõ nét, những chưởng quyền Ô long giữa bạt ngàn rừng núi, những pha hài siêu vui nhộn, cộng thêm kiến thức lịch sử dày dặn.\n" +
                "Đội do thám Ô Long viện tìm đến vườn hướng dương của Dược Vương phủ thì thấy lầu Chiêu Dương đã bị thiêu rụi, hai Linh vật sống biến mất không tăm tích. May sao, Sư phụ Lông Mày Dài sáng suốt đã mua rẻ được chú ngựa mà Sa Khách Quý từng cưỡi từ động Lăng Hư về. Nhờ có nó dẫn đường, họ đã tìm đến được động Thái Ất Lăng Hư và tìm thấy cả hai Linh vật sống đã bị đông cứng dưới lớp băng dày.\n" +
                "Bà Ớt và Sư phụ Lông Mày Dài lập tức mang Rìu Trời ra, định chặt nát cả hai Linh vật sống để ngăn ngừa hậu hoạ. Thế nhưng Tiểu sư đệ lại động lòng trắc ẩn, không muốn để Miêu Nô phải hi sinh theo hai con Linh vật sống…");

        return view;
    }
}
