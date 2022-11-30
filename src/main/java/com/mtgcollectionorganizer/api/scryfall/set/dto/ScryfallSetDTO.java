package com.mtgcollectionorganizer.api.scryfall.set.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScryfallSetDTO {

    @JsonProperty("object")
    private String object;

    @JsonProperty("id")
    private String id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("mtgo_code")
    private String mtgoCode;

    @JsonProperty("arena_code")
    private String arenaCode;

    @JsonProperty("tcgplayer_id")
    private Integer tcgplayerId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("scryfall_uri")
    private String scryfallUri;

    @JsonProperty("search_uri")
    private String searchUri;

    @JsonProperty("released_at")
    private String releasedAt;

    @JsonProperty("set_type")
    private String setType;

    @JsonProperty("card_count")
    private Integer cardCount;

    @JsonProperty("printed_size")
    private Integer printedsize;

    @JsonProperty("digital")
    private Boolean digital;

    @JsonProperty("nonfoil_only")
    private Boolean nonfoilOnly;

    @JsonProperty("foil_only")
    private Boolean foilOnly;

    @JsonProperty("block_code")
    private String blockCode;

    @JsonProperty("block")
    private String block;

    @JsonProperty("icon_svg_uri")
    private String iconSvgUri;
}
