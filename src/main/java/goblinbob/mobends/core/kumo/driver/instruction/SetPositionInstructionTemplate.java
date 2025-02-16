package goblinbob.mobends.core.kumo.driver.instruction;

import goblinbob.bendslib.serial.ISerialInput;
import goblinbob.bendslib.serial.ISerialOutput;
import goblinbob.bendslib.serial.SerialHelper;
import goblinbob.mobends.core.data.IEntityData;
import goblinbob.mobends.core.kumo.IKumoInstancingContext;
import goblinbob.mobends.core.kumo.ISerialContext;
import goblinbob.mobends.core.kumo.driver.expression.ExpressionTemplate;

import java.io.IOException;

public class SetPositionInstructionTemplate extends InstructionTemplate
{
    public final String partName;
    public final ExpressionTemplate expressionX;
    public final ExpressionTemplate expressionY;
    public final ExpressionTemplate expressionZ;

    public SetPositionInstructionTemplate(String partName, ExpressionTemplate x, ExpressionTemplate y, ExpressionTemplate z)
    {
        this.partName = partName;
        this.expressionX = x;
        this.expressionY = y;
        this.expressionZ = z;
    }

    @Override
    public void serialize(ISerialOutput out)
    {
        super.serialize(out);

        SerialHelper.serializeNullable(expressionX, out);
        SerialHelper.serializeNullable(expressionY, out);
        SerialHelper.serializeNullable(expressionZ, out);
    }

    @Override
    public <D extends IEntityData> IInstruction<D> instantiate(IKumoInstancingContext<D> context)
    {
        return new SetPositionInstruction<>(this, context);
    }

    public static <D extends IEntityData, C extends ISerialContext<C, D>> SetPositionInstructionTemplate deserialize(C context, ISerialInput in) throws IOException
    {
        String partName = in.readString();

        ExpressionTemplate expressionX = SerialHelper.deserializeNullable(context, ExpressionTemplate::deserializeGeneral, in);
        ExpressionTemplate expressionY = SerialHelper.deserializeNullable(context, ExpressionTemplate::deserializeGeneral, in);
        ExpressionTemplate expressionZ = SerialHelper.deserializeNullable(context, ExpressionTemplate::deserializeGeneral, in);

        return new SetPositionInstructionTemplate(partName, expressionX, expressionY, expressionZ);
    }
}
