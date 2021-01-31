package com.example.smiecioltest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import java.util.List;

public class ScanReceipt_fragment extends Fragment implements LifecycleObserver {

     CameraView cameraView;
     Button scan;
     boolean isDetected = false;
     FirebaseVisionBarcodeDetectorOptions options;
     FirebaseVisionBarcodeDetector detector;


    public ScanReceipt_fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_scan_receipt_fragment, container, false);

        scan= view.findViewById(R.id.scan);
        cameraView= view.findViewById(R.id.cameraview);

        Dexter.withActivity(ScanReceipt_fragment.this.getActivity())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        setUpCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(ScanReceipt_fragment.this.getActivity().getApplicationContext(), "You have to accept permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        return view;
    }

    private void setUpCamera() {
        scan.setEnabled(isDetected);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDetected = !isDetected;
                scan.setEnabled(isDetected);
            }
        });

        cameraView.setLifecycleOwner(this);
        cameraView.addFrameProcessor(new FrameProcessor() {
            @Override
            public void process(@NonNull Frame frame) {
                processImage(getVisionImageFromFrame(frame));
            }
        });
    }

       private void processImage(FirebaseVisionImage image){
        if(!isDetected)
        {
            detector.detectInImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
                         processResult(firebaseVisionBarcodes);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ScanReceipt_fragment.this.getActivity().getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }
       }

       private void processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes){
          if(firebaseVisionBarcodes.size()>0){
              isDetected=true;
              scan.setEnabled(isDetected);
              for (FirebaseVisionBarcode item: firebaseVisionBarcodes){
                  int value_type = item.getValueType();
                  switch (value_type)
                  {
                      case FirebaseVisionBarcode.TYPE_TEXT:
                      {
                          createDialog(item.getRawValue());
                      }
                      break;

                      case FirebaseVisionBarcode.TYPE_URL:
                      {
                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getRawValue()));
                          startActivity(intent);
                      }
                      break;
                      case FirebaseVisionBarcode.TYPE_CONTACT_INFO:
                      {
                          String info = new StringBuilder("Product name: ")
                                  .append(item.getContactInfo().getName().getFormattedName())
                                  .toString();
                          createDialog(info);

                      }
                      break;
                      default:
                          break;

                  }
              }
          }
       }

       private void createDialog(String text){
           AlertDialog.Builder builder= new AlertDialog.Builder(ScanReceipt_fragment.this.getActivity().getApplicationContext());
           builder.setMessage(text)
                   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int which) {
                           dialogInterface.dismiss();

                       }
                   });
           AlertDialog dialog =builder.create();
           dialog.show();
       }



        private FirebaseVisionImage getVisionImageFromFrame(Frame frame){
            byte[] data =frame.getData();
            FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
                    .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                    .setHeight(frame.getSize().getHeight())
                    .setWidth(frame.getSize().getWidth())
                    .build();
                    //.setRotation(frame.getRotation())

            return FirebaseVisionImage.fromByteArray(data,metadata);
        }


}