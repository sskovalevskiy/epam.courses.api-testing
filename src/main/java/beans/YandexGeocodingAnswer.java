
package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class YandexGeocodingAnswer {

    @SerializedName("response")
    @Expose
    public Response response;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("response", response).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(response).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof YandexGeocodingAnswer) == false) {
            return false;
        }
        YandexGeocodingAnswer rhs = ((YandexGeocodingAnswer) other);
        return new EqualsBuilder().append(response, rhs.response).isEquals();
    }

}
