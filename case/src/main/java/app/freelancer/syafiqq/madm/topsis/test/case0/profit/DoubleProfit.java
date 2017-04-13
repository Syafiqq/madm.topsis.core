package app.freelancer.syafiqq.madm.topsis.test.case0.profit;

import app.freelancer.syafiqq.madm.topsis.core.factory.Profit;
import org.apache.commons.lang3.builder.ToStringBuilder;

/*
 * This <topsis> created by : 
 * Name         : syafiq
 * Date / Time  : 13 April 2017, 8:47 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DoubleProfit extends Profit
{
    private double value;

    public DoubleProfit(double value)
    {
        this.value = value;
    }

    public double getValue()
    {
        return this.value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}
