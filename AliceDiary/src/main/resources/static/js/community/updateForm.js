function deleteFile(num, postNum) {
				console.log("fileNum=" + num + "&postNum="+ postNum);
				let token = $("meta[name='_csrf']").attr("content");
				let header = $("meta[name='_csrf_header']").attr("content");
				let httpRequest = new XMLHttpRequest();
				let param = "num="+num+"&postNum="+postNum;	
				
				console.log(param);
				
				httpRequest.onreadystatechange = function() {
					if (httpRequest.readyState === XMLHttpRequest.DONE) {
						if (httpRequest.status === 200) {
						console.log("!!!" + httpRequest.response);
						let result = JSON.parse(httpRequest.response);
							alert('파일이 삭제되었습니다!');
							location.reload();
							
			
				
							
						} else {
							alert('request에 뭔가 문제가 있어요.');
						}
				}	
				};

				//POST로 요청
				httpRequest.open('POST', "./put/filedelete", true);
				httpRequest.setRequestHeader(header, token);
				httpRequest.setRequestHeader('Content-type',
						'application/x-www-form-urlencoded');
				httpRequest.send(param);
			}