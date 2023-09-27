package com.example.autosuggestion;

import lombok.*;
import org.springframework.data.annotation.Id;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.AutoComplete;
import com.redis.om.spring.annotations.AutoCompletePayload;

@Data
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Document
public class Location {
    @Id
    private String id;
    @AutoComplete @NonNull
    private String name;
    @AutoCompletePayload("name") @NonNull
    private String fullName;
    @AutoCompletePayload("name") @NonNull
    private String type;
    @AutoCompletePayload("name") @NonNull
    private String state;
    @AutoComplete @NonNull
    private String country;
    @NonNull
    private String hierarchyPath;
    @NonNull
    private String latitude;
    @NonNull
    private String longitude;
}
