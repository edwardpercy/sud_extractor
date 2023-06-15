package org.pamguard.x3.sud;


/**
 * Interface for decoding a single chunk of a .sud file. 
 * 
 * @author Jamie Macaulay
 *
 */
public interface ISudarDataHandler {
	
	/**
	 * Process a chunk for the specific handler.
	 * @param sudChunk - class which contains the chunk header and the data.
	 * @throws Exception - might be triggered for example if writing a wav file
	 */
	public void processChunk(Chunk sudChunk) throws Exception;

	/**
	 * Called whenever it is time to close the file
	 */
	public void close(); 
	
	/**
	 * Initialise the file handler. The start of the sud files contains xml chunks that define which handlers are
	 * needed by the file. init(...) is called with the xml info from the initial chunk which contains metadata such as 
	 * sample rate, number of channels etc. 
	 * 
	 * @param logFile - the log file. 
	 * @param innerXml - the xml data for the chunk type. 
	 * @param id - the id fo the chunks for this handler. 
	 */
	public void init(LogFileStream logFile, String innerXml, int id);
	

	/**
	 * Get the handler type. 
	 * @return the handler type. 
	 */
	public String getHandlerType();
	
	
	
	static public ISudarDataHandler createHandler(String ftype, SudParams sudFileData) throws FileFormatNotSupportedException {
		switch(ftype.toLowerCase()) {
				case "x3v2": return new X3Handler(sudFileData,  ftype);
				case "wav": return new WavFileHandler(sudFileData, ftype);
				case "txt": return new TxtFileHandler(sudFileData, ftype);
				case "csv": return new CsvFileHandler(sudFileData, ftype);
				default : throw new FileFormatNotSupportedException(String.format("FType %s not supported", ftype));
		}
	}
	


	/**
	 * Get the chunk IDs.
	 * @return the chunk IDs associated with this handler. 
	 */
	int[] getChunkID();

}
