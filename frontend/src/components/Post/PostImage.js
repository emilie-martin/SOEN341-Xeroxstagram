import "../../config";
import React from "react";

import "./SCSS/PostImage.scss";

export default function PostImage(props) {
	return (
		<div className="image-wrapper">
			<img src={`${global.config.BACKEND_URL}/picture/${props.pictureId}.jpg`} alt={`${props.pictureId}}`} />
		</div>
	);
}