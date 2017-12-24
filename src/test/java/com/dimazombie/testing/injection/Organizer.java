package com.dimazombie.testing.injection;

import com.dimazombie.annotations.AutoSearch;
import com.dimazombie.annotations.Component;

@Component
public class Organizer {
    private Assistant assistant;

    @AutoSearch
    public Organizer(Assistant assistant) {
        this.assistant = assistant;
    }

    public Assistant getAssistant() {
        return assistant;
    }

}
