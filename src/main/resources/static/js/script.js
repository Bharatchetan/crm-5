console.log("this is Script file");
/*alert("js is Activated")*/

/* advance jS fuction which has issue*/

const toggleSidebar = () => {
	if($(".sidebar").is(":visible")){
		//true
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
	}else{
		//false
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");
	}
}; 


/*Searching Funtion */

const search = () => {
//console.log("Searching...")
	let q = $("#search-input").val();
	if (q == "") {
		$(".search-result").hide();
	} else {
		console.log(q);
//		$(".search-result").show();
//		sending request to server
		let url=`http://localhost:8080/search/${q}`;
		fetch(url)
		.then((response)=>{
			return response.json();
		})
		.then((data)=>{
			console.log(data);
			//data..
			let text=`<div class='list-group'>`
			data.forEach((contact)=>{
				text+=`<a href='/user/${contact.cid}/contact' class='list-group-item list-group-action'>${contact.name}</a>`
			});
			text+=`</div>`;
			$(".search-result").html(text);
		$(".search-result").show();
		});
//		$(".search-result").show();
	}
};

/*Old JS fuction not workign*/
/*
function toggleSidebar(){
	const sidebar= document.getElementsByName("sidebar")[0];
	const sidebar= document.getElementsByName("content")[0];
	if(window.getComputedStyle(sidebar).display==="none"){
		sidebar.style.display="block";
		content.style.display="20%";
	}else{
		sidebar.style.display="none";
		content.style.display="0%";
	}
}
*/

