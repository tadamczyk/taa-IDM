import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

class DownloadsTableModel extends AbstractTableModel implements Observer{
  private static final String[] columnNames = {
    "URL", "Rozmiar", "Postęp", "Status"
  };

  private static final Class[] columnClasses = {
    String.class, String.class, JProgressBar.class, String.class
  };

  private ArrayList<Download> downloadList = new ArrayList<Download>();

  public void addDownload(Download download){
    download.addObserver(this);
    downloadList.add(download);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  public Download getDownload(int row){
    return (Download) downloadList.get(row);
  }

  public void clearDownload(int row){
    downloadList.remove(row);
    fireTableRowsDeleted(row, row);
  }

  public int getColumnCount(){
    return columnNames.length;
  }

  public String getColumnName(int col){
    return columnNames[col];
  }

  public Class<?> getColumnClass(int col){
    return columnClasses[col];
  }

  public int getRowCount(){
    return downloadList.size();
  }

  public Object getValueAt(int row, int col){
    Download download = (Download) downloadList.get(row);
    switch (col){
      case 0:
        return download.getUrl();
      case 1:
        int size = download.getSize();
        return (size == -1) ? "" : Integer.toString(size);
      case 2:
        return new Float(download.getProgress());
      case 3:
        return Download.STATUSES[download.getStatus()];
    }
    return "";
  }

  public void update(Observable o, Object arg){
    int index = downloadList.indexOf(o);
    fireTableRowsUpdated(index, index);
  }
}
