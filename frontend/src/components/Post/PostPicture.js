import "../../config";
import axios from "axios";
import React, { useState } from "react";

import "./SCSS/PostPicture.scs";

export default function PostPicture(props) {
  const [errorMsg, setErrorMsg] = useState("");

  const submit = event => {
    event.preventDefault();
    const data = new FormData();
    data.append("picture", event.target.picture.files[0]);
    data.append("caption", event.target.caption.value);
    
    axios.post(global.config.BACKEND_URL + "/picture", data)
    .then((response) => {
        props.history.push(`/post/${response.data.id}`);
      },
      e => {
        if (e.response) {
          if (e.response.status === 401) {
            setErrorMsg("You must be logged in.");
          } else {
            setErrorMsg(e.response.data.message);
          }
        } else {
          setErrorMsg("An unknown error occured.");
        }
      }
    );
  };

  return (
    <div className="postPicture">
      <form onSubmit={submit}>
        <h2>Upload a Picture</h2>
        <input name="picture" type="file" />
        <label>Caption</label>
        <textarea name="caption"></textarea>
        {errorMsg && <div className="error">Error: {errorMsg}</div>}
        <button className="button" type="submit">
          Post
        </button>
      </form>
    </div>
  );
}
