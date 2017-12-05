package com.dkohut.iconomi.generators;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;

import org.web3j.codegen.SolidityFunctionWrapperGenerator;

public class WrapperGenerator {
	
	private static String pathToFiles;
	private static String pathForOutput;
	private static String packageName;	
	
	private static final Logger logger = Logger.getLogger(WrapperGenerator.class.getName());

	public static void main(String[] args) {
		if(args.length >= 3) {
			pathToFiles = args[0].toString();
			pathForOutput = args[1].toString();
			packageName = args[2].toString();
			
			File directory = new File(pathToFiles);
			String[] solidityFiles = directory.list((file, name) -> {
				return name.endsWith(".bin");
			});
			
			Arrays.stream(solidityFiles)
				.map(name -> { 
					return name.substring(0, name.length() - 4);	
				})
				.forEach(WrapperGenerator::generateWrapper);
			
			logger.info("Wrapper generated");
		} else {
			throw new IllegalArgumentException("Incorrect number of arguments, you should give 3 arguments");
		}

	}
	
	private static void generateWrapper(String fileName) {
		String[] parameters = new String[] {
				"--solidityTypes",
				pathToFiles + "/" + fileName + ".bin",
				pathToFiles + "/" + fileName + ".abi",
				"-o",
				pathForOutput,
				"-p",
				packageName
		};
		
		try {
			SolidityFunctionWrapperGenerator.main(parameters);
		} catch(Exception e) {
			logger.info("Exception during wrapper generating\n" + e.getMessage() + "\n" + e.getStackTrace());
		}
		
	}

}
