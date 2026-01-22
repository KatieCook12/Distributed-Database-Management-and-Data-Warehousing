// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.Fine;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;

// Calculates the specific monthly fine payment sum.
public class MonthlyFinePaymentSumCalculation {

    // Creates a filtered list.
    public static FilteredList<Fine> filterFines(ObservableList<Fine> fineList, String monthTF) {

        // Converts 'monthTF' to a lowercase format.
        String lowerCaseFilter = monthTF.toLowerCase();

        // Creates a new filtered list.
        FilteredList<Fine> filteredData = new FilteredList<>(fineList, p -> true);

        // Filters 'fineList' by the 'monthTF'.
        filteredData.setPredicate(fine -> fine.getDatePaid().toLowerCase().contains(lowerCaseFilter));

        // Returns the filtered data.
        return filteredData;
    }

    // Calculates the total amount paid.
    public static int calculateTotalAmountPaid(FilteredList<Fine> filteredData) {

        // Sets 'totalAmountPaid' to 0.
        int totalAmountPaid = 0;

        // For each fine in the filtered data...
        for (Fine fine : filteredData) {

            // Gets the fine payment.
            String amountPaid = String.valueOf(fine.getFinePayment());

            // Removes non-numeric characters from the amount paid (£).
            String amountPaidString = amountPaid.replaceAll("[^0-9]", "");

            // Converts the amount to an integer and add it to the total.
            int amountPaidIntegers = Integer.parseInt(amountPaidString);
            totalAmountPaid += amountPaidIntegers;
        }

        // Returns the total amount paid in that specific month.
        return totalAmountPaid;
    }

    // Based on the filtered data, gets the sorted list.
    public static SortedList<Fine> getSortedData(FilteredList<Fine> filteredData, TableView tableView) {

        // Creates a new SortedList from the filteredData.
        SortedList<Fine> sortedData = new SortedList<>(filteredData);

        // Makes sure that the sorting of the sortedData is synchronized with the sorting of the provided JavaFX table.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Returns the sorted data.
        return sortedData;
    }
}
