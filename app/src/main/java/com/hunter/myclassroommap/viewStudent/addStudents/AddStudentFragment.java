package com.hunter.myclassroommap.viewStudent.addStudents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.ClassRoom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddStudentFragment extends Fragment implements AddStudentContract.View {

    private static final int RESULT_CANCELED = 999;
    private AddStudentContract.Presenter addStudentPresenter;
    private TextView firstNameStudent, lastNameStudent, middleNameStudent, ageStudent;
    private ImageView takePicture;
    private Spinner genderSpinner;
    private String[] spinnerValueGender;
    private GenderSpinnerAdapter genderSpinnerAdapter;
    private Button addStudentButton;
    private ProgressDialog progressDialog;
    Integer positionClass;
    private ClassRoom classRoom;
    private Context context;
    private static final String IMAGE_DIRECTORY = "/YourDirectName";
    private Context mContext;
    private int GALLERY = 1, CAMERA = 2;

    public static AddStudentFragment newInstance(ClassRoom classRoom) {
        AddStudentFragment instance =  new AddStudentFragment();
        instance.classRoom = classRoom;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        takePicture = view.findViewById(R.id.image_photo);
        firstNameStudent = view.findViewById(R.id.add_first_name_student);
        lastNameStudent = view.findViewById(R.id.add_last_name_student);
        middleNameStudent = view.findViewById(R.id.add_middle_name);
        ageStudent = view.findViewById(R.id.add_age_student);
        addStudentButton = view.findViewById(R.id.add_student_button);
        genderSpinner = view.findViewById(R.id.spinner_gender);


        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMultiplePermissions();
                showPictureDialog();
            }
        });
        spinnerValueGender = new String[]{
                "Male"  ,
                "Female"
        };

        genderSpinnerAdapter = new GenderSpinnerAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item);
        genderSpinnerAdapter.addAll(spinnerValueGender);
        genderSpinnerAdapter.add("Gender");


        genderSpinner.setAdapter(genderSpinnerAdapter);
        addStudentPresenter = new AddStudentViewModel(this, getActivity().getApplicationContext());
        positionClass = getActivity().getIntent().getIntExtra("classroomPosition",0);
        addStudentButton.setOnClickListener(v -> {
            addStudentFields();
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void requestMultiplePermissions() {
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
       if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            takePicture.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {  // have the object build the directory structure, if needed.
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void addStudentFields() {
        if (firstNameStudent.getText().toString().length() == 0) {
            firstNameStudent.setError("The First line is not filled!");
        } else if (lastNameStudent.getText().toString().length() == 0) {
            lastNameStudent.setError("The Second line is not filled!");
        } else if (middleNameStudent.getText().toString().length() == 0) {
            middleNameStudent.setError("The Third line is not filled!");
        } else if(ageStudent.getText().toString().length() == 0){
            ageStudent.setError("The Four line is not filled!");
        } else {
            try{
                String firstName = firstNameStudent.getText().toString().trim();
                String lastName = lastNameStudent.getText().toString().trim();
                String middleName = middleNameStudent.getText().toString().trim();
                String gender = genderSpinner.getSelectedItem().toString();
                int age = Integer.parseInt(ageStudent.getText().toString());


                addStudentPresenter.addButtonClicked(firstName, lastName, middleName, gender, age, classRoom.getId());

                progressDialog = ProgressDialog.show(getActivity(),"Adding new Student","loading...");

            } catch(NumberFormatException ex){
                Toast.makeText(getActivity(), "Do not write long numbers!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccess(String messageAlert) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), messageAlert, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        requireActivity().onBackPressed();
                    }
                },1000);
            }
        });
    }

    @Override
    public void onError(String messageAlert) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), messageAlert, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        requireActivity().onBackPressed();
                    }
                },1000);
            }
        });
    }

    public void setData(ClassRoom item) {
        this.classRoom = item;
    }
}
