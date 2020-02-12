import React from "react";
import axios from "axios";
import '../config'

export default function PostPicture() {
    function submit(event) {
        event.preventDefault();

        const data = new FormData();
        data.append('picture', event.target.picture.files[0]);
        data.append('caption', event.target.caption.value);

        axios.post(global.config.BACKEND_URL + "/picture", data)
            .then(
                (response) => {
                    console.log(response);
                },
                (error) => {
                    console.log(error);
                }
            )
    }

    return(
        <div className="postPicture">
            <form onSubmit={submit}>
                <label>File</label>
                <input name="picture" type="file"></input>
                <br/>
                <label>Caption</label>
                <input name="caption"></input>
                <br/>
                <button type="submit">
                    Post
                </button>
            </form>
        </div>
    );
}
