/**
 * neuroConstruct
 *
 * Software for developing large scale 3D networks of biologically realistic neurons
 * Copyright (c) 2007 Padraig Gleeson
 * UCL Department of Physiology
 *
 * Development of this software was made possible with funding from the
 * Medical Research Council
 *
 */

package ucl.physiol.neuroconstruct.mechanisms;

import java.io.*;

import ucl.physiol.neuroconstruct.project.*;
import ucl.physiol.neuroconstruct.utils.*;
import ucl.physiol.neuroconstruct.utils.units.*;
import ucl.physiol.neuroconstruct.simulation.SimEnvHelper;

/**
 * Cell Mechanism representing a leaky membrane
 *
 * @author Padraig Gleeson
 * @version 1.0.4
 *
 *
 */

public class PassiveMembraneMechanism extends DistMembraneMechanism
{
    ClassLogger logger = new ClassLogger("PassiveMembraneMechanism");

    public static final String REV_POTENTIAL = "Reversal Potential";

    public PassiveMembraneMechanism()
    {
        super.setDescription("A passive leak conductance on the cell membrane");
        super.setMechanismModel("Passive conductance Channel");
        super.setDefaultInstanceName("PassiveCond");
        super.setInstanceName("PassiveCond");

        addNewParameter(REV_POTENTIAL,
                        "Reversal potential of the channel type",
                        -60,
                        UnitConverter.voltageUnits[UnitConverter.NEUROCONSTRUCT_UNITS]);

        mechanismImpls = new MechanismImplementation[]
            {new MechanismImplementation(SimEnvHelper.NEURON,
                                       "templates/modFileTemplates/PassiveCond.mod"),
            new MechanismImplementation(SimEnvHelper.GENESIS,
                                       "templates/genesisTemplates/PassiveCond.g")};

         setPlotInfoFile(ProjectStructure.getXMLTemplatesDir()
                                       + System.getProperty("file.separator") +"PassiveCondPlots.xml");


    }

    public Object clone()
    {
        PassiveMembraneMechanism mech = new PassiveMembraneMechanism();
        for (int i = 0; i < physParamList.length; i++)
        {
            try
            {
                mech.setParameter(new String(physParamList[i].parameterName), physParamList[i].getValue());
            }
            catch (CellMechanismException ex)
            {
                return null;
            }
        }
        return mech;

    }

    public static void main(String[] args)
    {
        PassiveMembraneMechanism pas = new PassiveMembraneMechanism();



        try
        {
            pas.setInstanceName("pass2");
            pas.setParameter(PassiveMembraneMechanism.REV_POTENTIAL, 77);
            pas.printDetails();
            System.out.println("Rev pot: " + pas.getParameter(PassiveMembraneMechanism.REV_POTENTIAL));


            pas.createImplementationFile(SimEnvHelper.NEURON,
                                         UnitConverter.NEURON_UNITS,
                                         new File("../temp/test.mod"),
                                         null,
                                         true,
                true);


            pas.createImplementationFile(SimEnvHelper.GENESIS,
                                         UnitConverter.GENESIS_PHYSIOLOGICAL_UNITS,
                                         new File("../temp/test.g"),
                                         null,
                                         false,
                true);


        }
        catch (CellMechanismException ex)
        {
            ex.printStackTrace();
        }
    }


}
