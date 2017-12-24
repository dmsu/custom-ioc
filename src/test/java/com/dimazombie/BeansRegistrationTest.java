package com.dimazombie;

import com.dimazombie.testing.simple.Presentation;
import com.dimazombie.testing.simple.Training;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BeansRegistrationTest{

    @Test
    void shouldReturnRegisteredBean(){
        //GIVEN
        DependenciesConfig config = new DependenciesConfig();
        config.register(Training.class).complete();
        Injector injector = new Injector(config);

        //WHEN
        Training training = injector.get(Training.class);
        //THEN
        Assert.assertNotNull(training);
    }

    @Test
    void shouldReturnRegisteredByTypeBean(){
        //GIVEN
        DependenciesConfig config = new DependenciesConfig();
        config.register(Training.class).as(Presentation.class).complete();
        Injector injector = new Injector(config);

        //WHEN
        Presentation training = injector.get(Presentation.class);
        //THEN
        Assert.assertNotNull(training);
    }

    @Test
    void shouldReturnBeanRegisteredAsObject(){
        //GIVEN
        DependenciesConfig config = new DependenciesConfig();
        Training expected = new Training();

        config.register(expected).complete();
        Injector injector = new Injector(config);

        //WHEN
        Training training = injector.get(Training.class);
        //THEN
        Assert.assertEquals(training, expected);
    }
}
