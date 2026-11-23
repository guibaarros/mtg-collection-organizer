package com.mtgcollectionorganizer.api.scryfall.set.service;

import com.mtgcollectionorganizer.api.scryfall.set.client.ScryfallSetClient;
import com.mtgcollectionorganizer.api.scryfall.set.dto.ScryfallSetDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScryfallSetServiceTest {

    @Mock
    private ScryfallSetClient setClient;

    @InjectMocks
    private ScryfallSetService setService;

    @Test
    @DisplayName("deve delegar a busca de set ao client")
    void shouldDelegateToClient() {
        final ScryfallSetDTO set = ScryfallSetDTO.builder()
                .code("vow")
                .name("Innistrad: Crimson Vow")
                .build();
        when(setClient.getSetByCode("vow")).thenReturn(set);

        assertEquals(set, setService.getSetByCode("vow"));
        verify(setClient).getSetByCode("vow");
    }
}
