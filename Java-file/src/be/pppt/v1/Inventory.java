package be.pppt.v1;


import java.awt.Graphics2D;
import java.util.ArrayList;

import be.pppt.v1.Items.Hand;
import be.pppt.v1.Items.Item;
import be.pppt.v1.Items.SubMachineGun;



public class Inventory {
	private ArrayList<Item> myItems = new ArrayList<>();
	private int pointer = 0;
	private int maxSizeInventory = 40;
	
	private Item standaardItem = new Hand(Point.nullPoint(), 1, 70, Math.PI / 6, 100, 5);
	
	public Inventory(int maxSizeInventory, Item standaardItem) {
		super();
		this.maxSizeInventory = maxSizeInventory;
		this.standaardItem = standaardItem;
	}
	
	public Inventory(int maxSizeInventory) {
		super();
		this.maxSizeInventory = maxSizeInventory;
	}
	
	
	
	public void drawInventory(Graphics2D g2d, Point beginPoint) {
		Point bufPoint = new Point(beginPoint);
		
		for (int i = 0; i < getPointer(); i++) {
			this.getItem(i).drawItemInfoSmall(g2d, bufPoint);
			
			bufPoint.setY(bufPoint.getY() + 54);
		}
		
		this.getItem().drawItemInfo(g2d, bufPoint);
		bufPoint.setY(bufPoint.getY() + 90);
		
		for (int i = getPointer() + 1; i < getAmoutItemsInventory(); i++) {
			this.getItem(i).drawItemInfoSmall(g2d, bufPoint);
			
			bufPoint.setY(bufPoint.getY() + 54);
		}
		
		if (this.getItem() != standaardItem) {
			this.getItem(getMaxSizeInventory()).drawItemInfoSmall(g2d, bufPoint);
		}
		
	}

	public void upInventory()
	{
		if (pointer < myItems.size())
		{
			pointer++;
		}
		else {
			pointer = 0;
		}
		
		//System.out.println(getItem().getName() + " : " + getPointer());
	}
	
	public void downInventory()
	{
		if (pointer > 0)
		{
			pointer--;
		}
		else {
			pointer = myItems.size();
		}
		
		//System.out.println(getItem().getName() + " : " + getPointer());
	}
	
	public int getAmoutItemsInventory() {
		return myItems.size();
	}
	
	public boolean addItem(Item itemToAdd)
	{
		if (myItems.size() >= maxSizeInventory)
		{
			return false;
		}
		
		myItems.add(itemToAdd);
		
		return true;
	}
	
	public Item getItem()
	{
		return getItem(pointer);
	}
	
	public Item getItem(int newPointer)
	{
		if (newPointer >= 0 && newPointer < myItems.size())
		{
			return myItems.get(newPointer);
		}
		
		return standaardItem;
		
	}
	
	public boolean removeItem()
	{
		return removeItem(pointer);
	}
	
	public boolean removeItem(int pointerToRemove)
	{
		Item itemToRemove = this.getItem(pointerToRemove);
		
		return this.removeItem(itemToRemove);
	}
	
	public boolean removeItem(Item itemToRemove)
	{
		if (itemToRemove != standaardItem) {
			myItems.remove(itemToRemove);
			
			return true;
		}
		
		return false;
	}
	
	public int getPointer() {
		return pointer;
	}
	public void setPointer(int newPointer) {
		if (newPointer >= 0 && newPointer < maxSizeInventory)
		{
			this.pointer = newPointer;
		}
	}
	
	public int getMaxSizeInventory() {
		return maxSizeInventory;
	}
	public void setMaxSizeInventory(int maxSizeInventory) {
		this.maxSizeInventory = maxSizeInventory;
	}
	
	
}
