package com.example.thymeleaf.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.BoundingPoly;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.cloud.vision.v1.LocalizedObjectAnnotation;
import com.google.protobuf.ByteString;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class Visionservice {
  // public String analyzeLabelsFromImageUrl(String imageUrl) throws Exception {
  //   // 이미지 데이터를 URL에서 읽어오기
  //   URL url = new URL(imageUrl);
  //   ByteString imgBytes;
  //   try (InputStream in = url.openStream()) {
  //       imgBytes = ByteString.readFrom(in);
  //   }

  //   // Google Vision API용 이미지 및 요청 설정
  //   Image img = Image.newBuilder().setContent(imgBytes).build();
  //   Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build(); // LABEL_DETECTION 사용
  //   AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
  //           .addFeatures(feat)
  //           .setImage(img)
  //           .build();
  //   List<AnnotateImageRequest> requests = new ArrayList<>();
  //   requests.add(request);

  //   // JSON 키 파일 읽기
  //   ClassPathResource resource = new ClassPathResource("oauth2-447308-254060984bfd.json");
  //   try (InputStream credentialsStream = resource.getInputStream()) {
  //       ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
  //               .setCredentialsProvider(() -> GoogleCredentials.fromStream(credentialsStream))
  //               .build();

  //       try (ImageAnnotatorClient client = ImageAnnotatorClient.create(settings)) {
  //           // Vision API 호출
  //           BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);

  //           // 결과 처리
  //           StringBuilder stringBuilder = new StringBuilder();
  //           for (AnnotateImageResponse res : response.getResponsesList()) {
  //               if (res.hasError()) {
  //                   System.out.printf("Error: %s\n", res.getError().getMessage());
  //                   return "Error detected";
  //               }
  //               // 라벨 정보 추가
  //               res.getLabelAnnotationsList().forEach(label -> {
  //                   stringBuilder.append("Description: ").append(label.getDescription())
  //                           .append(", Score: ").append(label.getScore())
  //                           .append("\n");
  //               });
  //           }
  //           return stringBuilder.toString();
  //       }
  //   }
  // }
  public String detectObjectsFromImageUrl(String imageUrl) throws Exception {
    // 이미지 데이터를 URL에서 읽어오기
    URL url = new URL(imageUrl);
    ByteString imgBytes;
    try (InputStream in = url.openStream()) {
        imgBytes = ByteString.readFrom(in);
    }

    // Google Vision API용 이미지 및 요청 설정
    Image img = Image.newBuilder().setContent(imgBytes).build();
    Feature feat = Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION).build(); // OBJECT_LOCALIZATION 사용
    AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
            .addFeatures(feat)
            .setImage(img)
            .build();
    List<AnnotateImageRequest> requests = new ArrayList<>();
    requests.add(request);

    // JSON 키 파일 읽기
    ClassPathResource resource = new ClassPathResource("oauth2-447308-254060984bfd.json");
    try (InputStream credentialsStream = resource.getInputStream()) {
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(() -> GoogleCredentials.fromStream(credentialsStream))
                .build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create(settings)) {
            // Vision API 호출
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);

            // 결과 처리
            StringBuilder stringBuilder = new StringBuilder();
            for (AnnotateImageResponse res : response.getResponsesList()) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return "Error detected";
                }
                // 객체 감지 결과 처리
                for (LocalizedObjectAnnotation object : res.getLocalizedObjectAnnotationsList()) {
                    stringBuilder.append("Object: ").append(object.getName())
                            .append(", Score: ").append(object.getScore())
                            .append("\nBounding Poly: ").append(formatBoundingPoly(object.getBoundingPoly()))
                            .append("\n\n");
                }
            }
            return stringBuilder.toString();
        }
    }
}

// 바운딩 박스 좌표 포맷팅 메서드
private String formatBoundingPoly(BoundingPoly boundingPoly) {
    StringBuilder builder = new StringBuilder();
    boundingPoly.getVerticesList().forEach(vertex -> {
        builder.append(String.format("(x: %f, y: %f) ", vertex.getX(), vertex.getY()));
    });
    return builder.toString();
}


// public String extractTextFromImageUrl(String imageUrl) throws Exception {
//   URL url = new URL(imageUrl);
//   ByteString imgBytes;
//   try (InputStream in = url.openStream()) {
//       imgBytes = ByteString.readFrom(in);
//   }

//   Image img = Image.newBuilder().setContent(imgBytes).build();
//   Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
//   AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
//           .addFeatures(feat)
//           .setImage(img)
//           .build();
//   List<AnnotateImageRequest> requests = new ArrayList<>();
//   requests.add(request);

//   // JSON 파일 경로를 ClassPathResource로 설정
//   ClassPathResource resource = new ClassPathResource("oauth2-447308-254060984bfd.json");
//   try (InputStream credentialsStream = resource.getInputStream()) {
//       ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
//               .setCredentialsProvider(() -> GoogleCredentials.fromStream(credentialsStream))
//               .build();

//       try (ImageAnnotatorClient client = ImageAnnotatorClient.create(settings)) {
//           BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
//           StringBuilder stringBuilder = new StringBuilder();
//           for (AnnotateImageResponse res : response.getResponsesList()) {
//               if (res.hasError()) {
//                   System.out.printf("Error: %s\n", res.getError().getMessage());
//                   return "Error detected";
//               }
//               stringBuilder.append(res.getFullTextAnnotation().getText());
//           }
//           return stringBuilder.toString();
//       }
//   }
// }

}
