package com.example.doctruyenapp;

//public class AddStoryActivity extends AppCompatActivity {
//
//    EditText edtTitle, edtContent, edtImage;
//    Button btnUploadStory;
//    AppDatabase db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_story);
//
//        db = AppDatabase.getInstance(this);
//
//        edtTitle = findViewById(R.id.upload_title);
//        edtContent = findViewById(R.id.upload_content);
//        edtImage = findViewById(R.id.upload_story_image);
//
//        btnUploadStory = findViewById(R.id.btn_upload);
//
//        btnUploadStory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String title = edtTitle.getText().toString();
//                String content = edtContent.getText().toString();
//                String image = edtImage.getText().toString();
//
//                if (title.equals("") || content.equals("") || image.equals("")) {
//                    Toast.makeText(AddStoryActivity.this, "Chưa điền đầy đủ các phần", Toast.LENGTH_SHORT).show();
//                } else {
//                    Story story = new Story(title, content, image);
//                    db.storyDAO().addStory(story);
//                    int id = getIntent().getIntExtra("Id", 0);
//                    Account account =  db.accountDAO().getAccountById(id);
//                    Intent intent = new Intent(AddStoryActivity.this, MainActivity.class);
//                    //Send data to MainActivity
//                    intent.putExtra("phanquyen", account.role);
//                    intent.putExtra("id", id);
//                    intent.putExtra("email", account.email);
//                    intent.putExtra("tentk", account.username);
//
//                    startActivity(intent);
//                }
//            }
//        });
//    }
//}