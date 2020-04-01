import axios from "axios";
import InfiniteScroll from 'react-infinite-scroller';
import React, { useEffect, useState } from 'react';

import Post from "../Post/Post";

import "./Feed.scss";

export default function Feed(props) {
    const [posts, setPosts] = useState([]);
    const [hasMorePosts, setHasMorePosts] = useState(true);
    const [errorMsg, setErrorMsg] = useState("");

    const loadMorePosts = () => {
        const lastPost = posts.length > 0 ? posts[posts.length-1] : 0;
        axios.get(global.config.BACKEND_URL + "/picture/feed?after=" + lastPost).then(
            (response) => {
                const newPosts = response.data;
                if (newPosts && newPosts.length) {
                    setPosts(posts.concat(newPosts));
                } else {
                    setHasMorePosts(false);
                }
            }
        ).catch(
            (error) => {
                setHasMorePosts(false);
                if(error.response && error.response.data && error.response.data.message)
                {
                    setErrorMsg(error.response.data.message);
                }
                else
                {
                    setErrorMsg("An unknown error occurred.");
                }
            }
        )
    }

    useEffect(() => {
        if (props.currentUser) {
            setPosts([]);
            loadMorePosts();
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [props.currentUser]);

    return (
        <div>
            {
                (!posts.length && !hasMorePosts) ?
                    <div className="feed-text">
                        <br/>
                        <br/>
                        <img src={require("./../../images/sad_face.png")} alt={"Sad face"}/>
                        <br/>
                        <br/>
                        <div>Oops! Looks like nothing's here...</div>
                        <br/>
                        <div>Start following people to see their most recent posts!</div>
                    </div> :
                    props.currentUser &&
                    <InfiniteScroll
                        loadMore={loadMorePosts}
                        hasMore={hasMorePosts}
                        initialLoad={false}
                        threshold={0}
                        loader={<div className="feed-text" key={0}>Loading...</div>}
                    >
                        <div>
                            {
                                posts.map((id) =>
                                    <div className="single-post" key={id}>
                                        <Post id={id}/>
                                        <br/>
                                    </div>
                                )
                            }
                        </div>
                    </InfiniteScroll>
                }
                {errorMsg && <div className="error">{errorMsg}</div>}
        </div>
    );
}