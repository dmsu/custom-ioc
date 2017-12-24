package com.dimazombie;

import com.dimazombie.testing.simple.Training;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BeansRegistrationTest{

    @Test
    void shouldReturnRegisteredBean(){
        //GIVEN
        DependenciesConfig config = new DependenciesConfig();
        config.register(Training.class);
        Injector injector = new Injector(config);

        //WHEN
        Training training = injector.get(Training.class);
        //THEN
        Assert.assertNotNull(training);
    }
}
