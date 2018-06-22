package kz.production.kuanysh.sellings.ui.content_suppiler.fragments.consignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import kz.production.kuanysh.sellings.R;
import kz.production.kuanysh.sellings.model.ConsignmentItem;

import static kz.production.kuanysh.sellings.ui.content_suppiler.SupplierActivity.SUPPLIER_VISIBLE_FRAGMENT_TAG;

public class ConsignmentItemControlFragment extends Fragment {
    private TextView control_name,control_time,control_price;
    private ImageView control_image,back;
    private EditText control_summa;
    private Button save;
    private ConsignmentItem consignmentItem;


    public ConsignmentItemControlFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_consignment_item_control, container, false);

        final Bundle bundle = getArguments();
        if (bundle != null){
            consignmentItem = (ConsignmentItem) bundle.get(SupplierConsignmentFragment.KEY_CONSIGNMENT);
        }
        //initialize
        control_name=(TextView)view.findViewById(R.id.consignment_control_item_name);
        control_time=(TextView)view.findViewById(R.id.consignment_control_item_time);
        control_price=(TextView)view.findViewById(R.id.consignment_control_item_price);
        control_image=(ImageView)view.findViewById(R.id.consignment_control_item_image);
        control_summa=(EditText) view.findViewById(R.id.consignment_control_item_summa);
        save=(Button)view.findViewById(R.id.consignment_control_item_confirm_btn);

        back=(ImageView)view.findViewById(R.id.consignment_control_toolbar_drawer);
        getBack();


        control_name.setText(consignmentItem.getName());
        control_time.setText(consignmentItem.getTime());
        control_price.setText(consignmentItem.getPrice()+" тг");

        return view;
    }



    private void getBack(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.supplier_content_frame, new SupplierConsignmentFragment(), SUPPLIER_VISIBLE_FRAGMENT_TAG)
                        .commit();
            }
        });
    }

}
