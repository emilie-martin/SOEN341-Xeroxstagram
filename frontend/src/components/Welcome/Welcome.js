import React from "react";
import FadeIn from "react-fade-in";

import "./Welcome.scss";

export default function Welcome() {
	return (
		<div className="welcome-div">
			<FadeIn transitionDuration="1000" delay="500">
				<div>
						<br /><br />
						<img src={require("./../../images/logo/logo.png")} alt="Logo"/>
				</div>
				<br />
				<div> Stay and scroll for a while </div>
				<br/>
				<div> Register now and share your experiences </div>
			</FadeIn>
		</div>
	);
}
