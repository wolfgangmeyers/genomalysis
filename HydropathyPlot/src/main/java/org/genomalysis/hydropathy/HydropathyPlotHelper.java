package org.genomalysis.hydropathy;

import java.util.HashMap;
import java.util.Map;

public class HydropathyPlotHelper
{
  private static final Map<Character, Double> scale = new HashMap(40);

  public static double averageHydrophobicity(String sequence, int index, int windowSize)
  {
    float result = 0F;
    if ((index >= 0) && (index < sequence.length()))
    {
      float sum = 0F;
      int halfSize = (int)Math.floor(windowSize / 2.0D);
      int residueCount = 0;

      int start = Math.max(0, index - halfSize);
      int end = Math.min(sequence.length(), index + halfSize + 1);
      for (int i = start; i < end; ++i) {
        char residue = sequence.charAt(i);
        double hydrophobicity = ((Double)scale.get(Character.valueOf(residue))).doubleValue();
        sum = (float)(sum + hydrophobicity);
        ++residueCount;
      }
      result = sum / residueCount;
    }

    return result;
  }

  static
  {
    scale.put(Character.valueOf('A'), Double.valueOf(1.8D));
    scale.put(Character.valueOf('R'), Double.valueOf(-4.5D));
    scale.put(Character.valueOf('N'), Double.valueOf(-3.5D));
    scale.put(Character.valueOf('D'), Double.valueOf(-3.5D));
    scale.put(Character.valueOf('C'), Double.valueOf(2.5D));
    scale.put(Character.valueOf('Q'), Double.valueOf(-3.5D));
    scale.put(Character.valueOf('E'), Double.valueOf(-3.5D));
    scale.put(Character.valueOf('G'), Double.valueOf(-0.40000000000000002D));
    scale.put(Character.valueOf('H'), Double.valueOf(-3.2000000000000002D));
    scale.put(Character.valueOf('I'), Double.valueOf(4.5D));
    scale.put(Character.valueOf('L'), Double.valueOf(3.7999999999999998D));
    scale.put(Character.valueOf('K'), Double.valueOf(-3.8999999999999999D));
    scale.put(Character.valueOf('M'), Double.valueOf(1.8999999999999999D));
    scale.put(Character.valueOf('F'), Double.valueOf(2.7999999999999998D));
    scale.put(Character.valueOf('P'), Double.valueOf(-1.6000000000000001D));
    scale.put(Character.valueOf('S'), Double.valueOf(-0.80000000000000004D));
    scale.put(Character.valueOf('T'), Double.valueOf(-0.69999999999999996D));
    scale.put(Character.valueOf('W'), Double.valueOf(-0.90000000000000002D));
    scale.put(Character.valueOf('Y'), Double.valueOf(-1.3D));
    scale.put(Character.valueOf('V'), Double.valueOf(4.2000000000000002D));
  }
}