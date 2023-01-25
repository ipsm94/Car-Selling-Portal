package autoseller;

import javax.swing.JOptionPane;

/**
 * Auto Seller - AutoSellerException Class
 * This in my main exception class for the Auto Seller application
 * The class extends exception.
 * The class is currently only used in the login class.
 * Further utilization of AutoSellerException for all classes is in progress.
 */
public class AutoSellerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public AutoSellerException(String e) {
		super(e);
	}
	
   @Override
   /**
    * Overrides default toString method of exception class
    * with custom one - for aesthetic purposes.
    */
    public String toString()
    {
        return "Error: " + this.getMessage();
    }
  
}
