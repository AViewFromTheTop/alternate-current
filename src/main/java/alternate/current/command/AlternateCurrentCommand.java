package alternate.current.command;

import alternate.current.AlternateCurrentMod;
import alternate.current.util.profiler.ProfilerResults;

import net.minecraft.server.command.Command;
import net.minecraft.server.command.ICommand;
import net.minecraft.server.command.exception.CommandException;
import net.minecraft.server.command.exception.IncorrectUsageException;
import net.minecraft.server.command.source.CommandSource;
import net.minecraft.text.Text;

public class AlternateCurrentCommand extends Command {

	@Override
	public String getName() {
		return "alternatecurrent";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getUsage(CommandSource source) {
		return AlternateCurrentMod.DEBUG ? "/alternatecurrent [on/off/resetProfiler]" : "/alternatecurrent [on/off]";
	}

	@Override
	public void run(CommandSource source, String[] args) throws CommandException {
		switch (args.length) {
		case 0:
			query(source);
			return;
		case 1:
			String arg = args[0];

			switch (arg) {
			case "on":
				set(source, true);
				return;
			case "off":
				set(source, false);
				return;
			case "resetProfiler":
				if (AlternateCurrentMod.DEBUG) {
					sendSuccess(source, "profiler results have been cleared!");

					ProfilerResults.log();
					ProfilerResults.clear();

					return;
				}
			}

			break;
		}

		throw new IncorrectUsageException(getUsage(source));
	}

	private void query(CommandSource source) {
		String state = AlternateCurrentMod.on ? "enabled" : "disabled";
		source.sendMessage(Text.literal(String.format("Alternate Current is currently %s", state)));
	}

	private void set(CommandSource source, boolean on) {
		AlternateCurrentMod.on = on;

		String state = AlternateCurrentMod.on ? "enabled" : "disabled";
		sendSuccess(source, String.format("Alternate Current has been %s!", state));
	}

	@Override
	public int compareTo(Object o) {
		return super.compareTo((ICommand)o);
	}
}
