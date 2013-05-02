package code.pliant.common.maven;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang.SystemUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.toolchain.Toolchain;
import org.apache.maven.toolchain.ToolchainManager;

import code.pliant.common.core.Strings;

/**
 * Provides utilities for executing processes outside of the Maven runtime.
 * 
 * 
 * @author Daniel Rugg
 */
public class Executions {

	/**
	 * Obtains the java executable from the Maven Toolchain.
	 * 
	 * @param session
	 * @param toolchainManager
	 * @return
	 * @throws MojoExecutionException
	 */
	public static String getJavaExecutable(MavenSession session, ToolchainManager toolchainManager)
			throws MojoExecutionException {
		return getExecutable(session, toolchainManager, "jdk", "java");
	}

	/**
	 * Obtains the java executable from the Maven Toolchain.
	 * 
	 * @param session
	 * @param toolchainManager
	 * @param type
	 *            The type of tool chain to look for in maven.
	 * @param name
	 *            The name of the executable file that would be called in in the path.
	 * @return
	 * @throws MojoExecutionException
	 */
	public static String getExecutable(MavenSession session, ToolchainManager toolchainManager, String type, String name)
			throws MojoExecutionException {
		Toolchain toolchain = toolchainManager.getToolchainFromBuildContext(type, session);
		if (toolchain != null) {
			String tool = toolchain.findTool(name);
			if (Strings.isValid(tool)) {
				return tool;
			}
			else {
				throw new MojoExecutionException("Unable to find 'java' executable for toolchain: " + toolchain);
			}
		}
		return name;
	}

	/**
	 * Generates a CommandLine to execute a program.
	 * 
	 * @param mode
	 * @param executable
	 * @return
	 */
	public static CommandLine getCommandLine(ExecutionMode mode, String executable) {
		CommandLine command = null;
		if (ExecutionMode.INTERACTIVE.equals(mode) && SystemUtils.IS_OS_WINDOWS) {
			command = new CommandLine("cmd");
			command.addArgument("/c");
			command.addArgument("start");
			command.addArgument(executable);
		}
		else {
			command = new CommandLine(executable);
		}
		return command;
	}

	/**
	 * Generates the a default executer with a PumpStreamHandler and the Maven project base directory 
	 * as the working directory.
	 * 
	 * @param session
	 * @throws MojoExecutionException
	 */
	public static Executor getDefaultExecutor(MavenSession session) throws MojoExecutionException {
		Executor executor = new DefaultExecutor();
		ExecuteStreamHandler handler = new PumpStreamHandler(System.out, System.err, System.in);
		executor.setStreamHandler(handler);
		executor.setWorkingDirectory(session.getCurrentProject().getBasedir());
		return executor;
	}

	/**
	 * Executes a CommandLine, throwing an exception if anything fails.
	 * 
	 * @param session
	 * @param command
	 * @param executor
	 * @param environment
	 * @throws MojoExecutionException
	 */
	@SuppressWarnings("rawtypes")
	public static void execute(MavenSession session, CommandLine command, Executor executor, Map environment) throws MojoExecutionException {
		if(executor == null){
			executor = getDefaultExecutor(session);
		}
		int status = 0;
		try {
			status = executor.execute(command, environment);
		}
		catch (ExecuteException e) {
			status = e.getExitValue();
		}
		catch (IOException e) {
			status = 1;
		}

		if (status != 0) {
			throw new MojoExecutionException("Execution of command failed.");
		}
	}
	
	/**
	 * Executes a java command from within 
	 * @param session
	 * @param toolchainManager
	 * @param mode
	 * @param mainClass
	 * @param vmargs
	 * @param mainArgs
	 * @throws MojoExecutionException
	 */
	@SuppressWarnings("rawtypes")
	public static void executeJava(MavenSession session, ToolchainManager toolchainManager, ExecutionMode mode, 
			String mainClass, String[] vmargs, String[] mainArgs) throws MojoExecutionException{
		String javaExecutable = getJavaExecutable(session, toolchainManager);
		CommandLine command = getCommandLine(mode, javaExecutable);
		if(vmargs != null){
			command.addArguments(vmargs, false);
		}
		command.addArgument(mainClass);
		if(mainArgs != null){
			command.addArguments(mainArgs, false);
		}
		execute(session, command, null, new HashMap());
	}
	
	/**
	 * Puts quotes around a string if it contains any spaces.
	 * @param arg
	 * @return
	 */
	public static String quoteArg(String arg){
		if(Strings.isValid(arg) && arg.contains(" ")){
			StringBuilder builder = new StringBuilder()
				.append('"').append(arg).append('"');
			return builder.toString();
		}
		return arg;
	}
}
