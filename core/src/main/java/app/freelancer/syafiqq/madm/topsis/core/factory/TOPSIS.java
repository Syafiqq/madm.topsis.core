package app.freelancer.syafiqq.madm.topsis.core.factory;

import app.freelancer.syafiqq.madm.topsis.core.factory.interfaces.Compressable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * This <topsis> created by : 
 * Name         : syafiq
 * Date / Time  : 13 April 2017, 4:34 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class TOPSIS
{
    private @NotNull  List<Alternative>    alternatives;
    private @Nullable AccumulatorContainer decisionMatrixAccumulator;
    private @Nullable WeightContainer      weight;
    private @Nullable ProfitContainer      profit;
    private @Nullable ProfitContainer      loss;

    public TOPSIS()
    {
        this.alternatives = new LinkedList<>();
    }

    public void process()
    {
        this.compile();
        this.collectData();
        this.calculate();
        this.sort();
    }

    private void compile()
    {
        if(this.alternatives.size() <= 0)
        {
            System.err.println("At least one alternative exists");
            System.exit(0);
        }

        if(!(this.decisionMatrixAccumulator instanceof Compressable))
        {
            System.err.println("Decision Matrix Accumulator must implement compressable");
            System.exit(0);
        }

        if(this.weight == null)
        {
            System.err.println("Weight Container must be initialized");
            System.exit(0);
        }
    }

    private void collectData()
    {
        for(Alternative alternative : this.alternatives)
        {
            alternative.collectData(this.decisionMatrixAccumulator);
        }
        ((Compressable) this.decisionMatrixAccumulator).compress();
    }

    private void calculate()
    {
        this.calculateDecisionMatrix();
        this.calculateWeightedDecisionMatrix();
        this.collectProfitAndLoss();
        this.collectProfitAndLossDistance();
        this.ranking();
    }

    private void calculateDecisionMatrix()
    {
        for(Alternative alternative2 : this.alternatives)
        {
            alternative2.calculateDecisionMatrix(this.decisionMatrixAccumulator);
        }
    }

    private void calculateWeightedDecisionMatrix()
    {
        for(Alternative alternative2 : this.alternatives)
        {
            alternative2.calculateWeightedDecisionMatrix(this.weight);
        }
    }

    private void collectProfitAndLoss()
    {
        this.profit = this.alternatives.get(0).adaptWeightedDecisionMatrix();
        this.loss = this.alternatives.get(0).adaptWeightedDecisionMatrix();

        if((this.profit == null) || (this.loss == null))
        {
            System.err.println("Profit and Loss must be implemented");
            System.exit(0);
        }

        for(@NotNull final Alternative alternative1 : this.alternatives)
        {
            alternative1.getProfit(this.profit);
            alternative1.getLoss(this.loss);
        }
    }

    private void collectProfitAndLossDistance()
    {
        for(@NotNull final Alternative alternative : this.alternatives)
        {
            alternative.calculateProfitDistance(this.profit);
            alternative.calculateLossDistance(this.loss);
        }
    }

    private void ranking()
    {
        for(@NotNull final Alternative alternative : this.alternatives)
        {
            alternative.calculatePreferences();
        }
    }

    private void sort()
    {
        Collections.sort(this.alternatives, new Comparator<Alternative>()
        {
            @Override public int compare(Alternative o1, Alternative o2)
            {
                return o1.compareTo(o2);
            }
        });
    }

    public Alternative getBestAlternative()
    {
        @Nullable Alternative best = null;
        if(alternatives.size() > 0)
        {
            @NotNull final Alternative first = this.alternatives.get(0);
            @NotNull final Alternative last  = this.alternatives.get(this.alternatives.size() - 1);
            best = first.compareTo(last) >= 0 ? last : first;
        }
        else
        {
            System.err.println("At least one alternative exists");
            System.exit(0);
        }
        return best;
    }

    public boolean addAlternative(@NotNull Alternative t)
    {
        return alternatives.add(t);
    }

    @NotNull public List<Alternative> getAlternatives()
    {
        return this.alternatives;
    }

    public AccumulatorContainer getDecisionMatrixAccumulator()
    {
        return this.decisionMatrixAccumulator;
    }

    public void setDecisionMatrixAccumulator(AccumulatorContainer decisionMatrixAccumulator)
    {
        this.decisionMatrixAccumulator = decisionMatrixAccumulator;
    }

    public WeightContainer getWeight()
    {
        return this.weight;
    }

    public void setWeight(WeightContainer weight)
    {
        this.weight = weight;
    }

    public ProfitContainer getProfit()
    {
        return this.profit;
    }

    public void setProfit(ProfitContainer profit)
    {
        this.profit = profit;
    }

    public ProfitContainer getLoss()
    {
        return this.loss;
    }

    public void setLoss(ProfitContainer loss)
    {
        this.loss = loss;
    }
}
