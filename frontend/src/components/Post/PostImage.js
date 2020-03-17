import '../../config';
import React from "react";

import "./PostImage.scss";

export default function PostImage(pictureId) {
	return (
		<div className="image-wrapper">
			<img src={`${global.config.BACKEND_URL}/picture/${pictureId.pictureId}.jpg`} alt={`${typeof(pictureId.pictureId)}}`} />
		</div>
	);
}