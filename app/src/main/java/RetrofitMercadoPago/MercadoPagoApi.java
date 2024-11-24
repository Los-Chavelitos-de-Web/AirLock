package RetrofitMercadoPago;

import java.util.List;

import Model.ProdCart;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MercadoPagoApi {
    @POST("/create-preference")
    Call<CreatePreferenceResponse> createPreference(@Body List<ProdCart> relojes);
}
