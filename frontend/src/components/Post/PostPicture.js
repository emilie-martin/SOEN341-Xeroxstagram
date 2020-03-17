import React from "react";
import { useState } from "react";
import axios from "axios";
import "../../config";

export const PostPicture = props => {
  const [errorMsg, setErrorMsg] = useState("");

  const submit = event => {
    event.preventDefault();
    const data = new FormData();
    data.append("picture", event.target.picture.files[0]);
    data.append("caption", event.target.caption.value);
    axios.post(global.config.BACKEND_URL + "/picture", data).then(
      response => {
        this.props.history.push(`/post/${response.data.id}`);
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
        <label>File</label>
        <input name="picture" type="file" />
        <br />
        <label>Caption</label>
        <input name="caption" />
        <br />
        {errorMsg && <div className="error">Error: {errorMsg}</div>}
        <button type="submit">Post</button>
      </form>
    </div>
  );
};
export default PostPicture;
