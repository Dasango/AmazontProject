package Tabs;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Views {

	private Parent view;

	public Views() {

	}

	public Parent getHomeView() throws IOException {
		this.view = FXMLLoader.load(getClass().getResource("/resources/HomeView.fxml"));
		return this.view;
	}

	public Parent getProductView() throws IOException {
		this.view = FXMLLoader.load(getClass().getResource("/resources/ProductAdd.fxml"));
		return this.view;
	}

	public Parent getUserView() throws IOException {
		this.view = FXMLLoader.load(getClass().getResource("/resources/UserAdd.fxml"));
		return this.view;
	}

	public Parent getCategoryView() throws IOException {
		this.view = FXMLLoader.load(getClass().getResource("/resources/CategoryAdd.fxml"));
		return this.view;
	}

	public Parent getCategoryEView() throws IOException {
		this.view = FXMLLoader.load(getClass().getResource("/resources/CategoryEdit.fxml"));
		return this.view;
	}

	public Parent getUserEView() throws IOException {
		this.view = FXMLLoader.load(getClass().getResource("/resources/UserEdit.fxml"));
		return this.view;
	}
	public Parent getProductEView() throws IOException {
		this.view = FXMLLoader.load(getClass().getResource("/resources/ProductEdit.fxml"));
		return this.view;
	}
}
