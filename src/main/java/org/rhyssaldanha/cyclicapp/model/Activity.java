package org.rhyssaldanha.cyclicapp.model;


import org.mongojack.Id;

import java.util.UUID;

public record Activity(@Id UUID id, String name) {
}
