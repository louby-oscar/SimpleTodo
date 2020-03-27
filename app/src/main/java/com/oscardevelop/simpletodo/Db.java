package com.oscardevelop.simpletodo;

public class Db
{
	
		private int id;
	    private String taskName, date;
	    


		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		public void setTaskName(String taskName){
			this.taskName=taskName;
		}
		public String getTaskName(){
			return taskName;
		}
		public void setDate(String date){
			this.date=date;
		}

		public String getDate(){
			return date;
		}

}
