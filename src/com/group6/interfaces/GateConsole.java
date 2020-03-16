package com.group6.interfaces;
// Generated by Together

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.group.data.PassengerDetails;
import com.group6.database.AircraftManagementDatabase;
import com.group6.database.GateInfoDatabase;

/**
 * An interface to SAAMS: Gate Control Console: Inputs events from gate staff,
 * and displays aircraft and gate information. This class is a controller for
 * the GateInfoDatabase and the AircraftManagementDatabase: sends messages when
 * aircraft dock, have finished disembarking, and are fully emarked and ready to
 * depart. This class also registers as an observer of the GateInfoDatabase and
 * the AircraftManagementDatabase, and is notified whenever any change occurs in
 * those <<model>> elements. See written documentation.
 * 
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:view:::id1un8dcko4qme4cko4sw27
 * @url element://model:project::SAAMS/design:view:::id1jkohcko4qme4cko4svww
 * @url element://model:project::SAAMS/design:node:::id1un8dcko4qme4cko4sw27.node61
 */
public class GateConsole extends JFrame implements Observer, ActionListener {
	/**
	 * The GateConsole interface has access to the GateInfoDatabase.
	 * 
	 * @supplierCardinality 1
	 * @clientCardinality 0..*
	 * @label accesses/observes
	 * @directed
	 */
	GateInfoDatabase gateInfoDatabase;

	/**
	 * The GateConsole interface has access to the AircraftManagementDatabase.
	 * 
	 * @supplierCardinality 1
	 * @clientCardinality 0..*
	 * @directed
	 * @label accesses/observes
	 */
	private AircraftManagementDatabase AircraftManagementDatabase;

	/**
	 * This gate's gateNumber - for identifying this gate's information in the
	 * GateInfoDatabase.
	 */
	private int gateNumber;

	private int currentGate;

	private JButton flightDocked;
	private JButton flightUnloaded;
	private JButton addPassenger;
	private JButton flightLoaded;
	private JLabel flightCode;
	private JLabel flightStatus;
	private JLabel flightDestination;
	private JLabel gateStatus;

	public GateConsole(AircraftManagementDatabase AircraftManagementDatabase, GateInfoDatabase gateInfoDatabase,
			int gateNumber) {

		this.AircraftManagementDatabase = AircraftManagementDatabase;

		this.gateInfoDatabase = gateInfoDatabase;

		this.gateNumber = gateNumber;

		currentGate = gateInfoDatabase.getStatuses()[gateNumber];

		setTitle("Gate Console");
		setLocation(40, 40);
		setSize(500, 350);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		Container window = getContentPane();
		window.setLayout(new FlowLayout());

		flightDocked = new JButton("Dock Plane");
		window.add(flightDocked);
		flightDocked.addActionListener(this);

		flightUnloaded = new JButton("Vacant Plane");
		window.add(flightUnloaded);
		flightUnloaded.addActionListener(this);

		addPassenger = new JButton("Add Passenger to Plane");
		window.add(addPassenger);
		addPassenger.addActionListener(this);

		flightLoaded = new JButton("Leave Airspace");
		window.add(flightLoaded);
		flightLoaded.addActionListener(this);

		flightCode = new JLabel();
		window.add(flightCode);
		flightStatus = new JLabel();
		window.add(flightStatus);
		flightDestination = new JLabel();
		window.add(flightDestination);
		gateStatus = new JLabel();
		window.add(gateStatus);

		updateInfoOfGate();

		updateGateStatus();

	}

	private void updateInfoOfGate() {
		// TODO Auto-generated method stub

	}

	private void updateGateStatus() {
		String statusOfGate = String.valueOf(gateInfoDatabase.getStatus(gateNumber));
		gateStatus.setText(statusOfGate);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == flightDocked)
			dockFlight();
		if (e.getSource() == flightUnloaded)
			unloadFlight();
		if (e.getSource() == addPassenger)
			addPassengerToFlight();
		if (e.getSource() == flightLoaded)
			loadFlight();
	}

	private void dockFlight() {
		int[] mCodes = AircraftManagementDatabase.getWithStatus(6);
		int mCode = mCodes[0];
		AircraftManagementDatabase.setStatus(mCode, 7);
		gateInfoDatabase.docked(this.gateNumber);

	}

	private void unloadFlight() {
		int[] mCodes = AircraftManagementDatabase.getWithStatus(7);
		int mCode = mCodes[0];
		AircraftManagementDatabase.setStatus(mCode, 8);
	}

	private void addPassengerToFlight() {
		PassengerDetails passengerName = new PassengerDetails(JOptionPane.showInputDialog("Passenger Name"));

		int[] mCodes = AircraftManagementDatabase.getWithStatus(14);
		int mCode = mCodes[0];
		AircraftManagementDatabase.addPassenger(mCode, passengerName);

	}

	private void loadFlight() {
		AircraftManagementDatabase.getWithStatus(14);

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}