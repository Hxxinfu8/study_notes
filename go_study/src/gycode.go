package main

import (
	"fmt"
	"github.com/GUAIK-ORG/go-snowflake/snowflake"
	"github.com/golang/glog"
	"sync"
	"time"
)

func main() {
	var wg sync.WaitGroup
	s, err := snowflake.NewSnowflake(int64(0), int64(0))
	if err != nil {
		glog.Error(err)
		return
	}
	var check sync.Map
	t1 := time.Now()
	for i := 0; i < 200000; i++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			val := s.NextVal()
			fmt.Println(val)
			if _, ok := check.Load(val); ok {
				// id冲突检查
				glog.Error(fmt.Errorf("error#unique: val:%v", val))
				return
			}
			check.Store(val, 0)
			if val == 0 {
				glog.Error(fmt.Errorf("error"))
				return
			}
		}()
	}
	wg.Wait()
	elapsed := time.Since(t1)
	glog.Infof("generate 20k ids elapsed: %v", elapsed)
}
