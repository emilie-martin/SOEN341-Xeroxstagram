import React, {useState} from "react";
import axios from "axios";
import "./PostPicture.scss";
import '../config'
import { useHistory } from "react-router-dom";

export default function PostPicture() {
    let history = useHistory();
    const [error, setError] = useState({msg: null});

    function submit(event) {
        event.preventDefault();

        const data = new FormData();
        data.append('picture', event.target.picture.files[0]);
        data.append('caption', event.target.caption.value);

        axios.post(global.config.BACKEND_URL + "/picture", data)
            .then(
                (response) => {
                    history.push(`/post/${response.data.id}`);
                },
                (e) => {
                    setError({msg: e.response.data.message});
                }
            )
    }

    return(
        <div className="postPicture">
            <form onSubmit={submit}>
                <label>File</label>
                <input name="picture" type="file"/>
                <br/>
                <label>Caption</label>
                <input name="caption"/>
                <br/>
                {error.msg && <div className="error">Error: {error.msg}</div>}
                <button type="submit">
                    Post
                </button>
            </form>
        </div>
    );
}
