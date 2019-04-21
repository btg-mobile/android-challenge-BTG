package br.com.ricardo.filmespopulares;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import br.com.ricardo.filmespopulares.data.network.api.ApiService;
import br.com.ricardo.filmespopulares.data.network.api.FilmService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @Mock
    private static ApiService apiServiceMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception{

        apiServiceMock = new ApiService();
        Assert.assertNotNull(apiServiceMock);
        Assert.assertTrue(true);
    }

    @Test
    public final void testService(){

        if(apiServiceMock == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("www.google.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiServiceMock = (ApiService) retrofit.create(FilmService.class);
        }
    }
}
