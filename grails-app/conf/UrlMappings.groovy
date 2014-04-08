class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/captcha/$action?/$id?"(controller: "captcha")
        "/captcha2/$action?/$id?"(controller: "captcha")

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
