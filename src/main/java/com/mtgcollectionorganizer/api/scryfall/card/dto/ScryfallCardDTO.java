package com.mtgcollectionorganizer.api.scryfall.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScryfallCardDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("set")
    private String set;

    @JsonProperty("collector_number")
    private String collectorNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("printed_name")
    private String printedName;

    @JsonProperty("mana_cost")
    private String manaCost;

    @JsonProperty("cmc")
    private Integer cmc;

    @JsonProperty("image_uris")
    private Map<String, String> imageUris;

    @JsonProperty("scryfall_uri")
    private String scryfallUri;

    @JsonProperty("type_line")
    private String typeLine;

    @JsonProperty("printed_type_line")
    private String printedTypeLine;

    @JsonProperty("oracle_text")
    private String oracleText;

    @JsonProperty("printed_text")
    private String printedText;

    @JsonProperty("rarity")
    private String rarity;

    @JsonProperty("related_uris")
    private Map<String, String> relatedUris;

    @JsonProperty("colors")
    private List<String> colors;

    @JsonProperty("color_identity")
    private List<String> colorIdentity;

}
